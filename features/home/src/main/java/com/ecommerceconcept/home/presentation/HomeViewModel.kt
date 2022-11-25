package com.ecommerceconcept.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecommerceconcept.categories.Category
import com.ecommerceconcept.common.utils.combine
import com.ecommerceconcept.favourites.model.FavouritesRepository
import com.ecommerceconcept.filters.Brand
import com.ecommerceconcept.filters.Filters
import com.ecommerceconcept.filters.Price
import com.ecommerceconcept.filters.Size
import com.ecommerceconcept.home.model.GeoApi
import com.ecommerceconcept.home.model.HomeRepository
import com.ecommerceconcept.my_cart.model.CartRepository
import com.ecommerceconcept.products.BestSellerProduct
import com.ecommerceconcept.products.Products
import dev.icerock.moko.errors.handler.ExceptionHandler
import dev.icerock.moko.errors.mappers.mapThrowable
import dev.icerock.moko.resources.desc.StringDesc
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(
    private val homeRepository: HomeRepository,
    private val exceptionHandler: ExceptionHandler,
    private val geoApi: GeoApi,
    private val cartRepository: CartRepository,
    private val favouritesRepository: FavouritesRepository
) : ViewModel() {

    private val defaultFilter = Filters(null, null, null, null)

    private val loadState = MutableStateFlow<LoadState>(LoadState.NotExecuted)

    private val productsRefreshing = MutableStateFlow(false)

    private val selectedCategory = MutableStateFlow<Category?>(null)
    private val selectedFilters = MutableStateFlow(defaultFilter)

    val state = combine(
        loadState,
        geoApi.getLocationFlow(),
        productsRefreshing,
        selectedCategory,
        selectedFilters,
        homeRepository.productsFlow(),
        homeRepository.categoriesFlow(),
        favouritesRepository.favouritesFlow(),
        cartRepository.cartFlow().map { it?.products?.size ?: 0 }
    ) { load, location, productsRefreshing, selectedCategory, selectedFilters, products, categories, favourites, cart ->
        when (load) {
            is LoadState.NotExecuted -> State.Loading
            is LoadState.Loading -> State.Loading
            is LoadState.LoadingError -> State.LoadingError(load.error)
            is LoadState.Loaded ->
                State.Loaded(
                    productsRefreshing,
                    location,
                    selectedFilters,
                    selectedCategory,
                    categories!!,
                    products!!,
                    favourites,
                    cart
                )
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), State.Loading)

    init {
        load()
    }

    fun load() {
        if (loadState.value == LoadState.Loading) return
        viewModelScope.launch {
            exceptionHandler.handle {
                loadState.value = LoadState.Loading
                geoApi.refreshLocation()
                cartRepository.refreshCartFlow()
                homeRepository.refreshProducts(selectedFilters.value, selectedCategory.value)
                homeRepository.refreshCategories()
                loadState.value = LoadState.Loaded
            }.catch<Exception> { error ->
                loadState.value = LoadState.LoadingError(error.mapThrowable())
                true
            }.execute()
        }
    }

    fun toggleFavourite(bestSellerProduct: BestSellerProduct) {
        require(loadState.value is LoadState.Loaded)
        favouritesRepository.toggleFavourite(bestSellerProduct.id)
    }

    fun isFavourite(bestSellerProduct: BestSellerProduct): Boolean {
        require(loadState.value is LoadState.Loaded)
        return favouritesRepository.isFavourite(bestSellerProduct.id)
    }

    fun updateSearch(searchText: String) {
        require(loadState.value is LoadState.Loaded)
        selectedFilters.value = selectedFilters.value.copy(search = searchText)
    }

    fun updateBrand(brand: Brand) {
        require(loadState.value is LoadState.Loaded)
        selectedFilters.value = selectedFilters.value.copy(brand = brand)
    }

    fun updatePrice(price: Price) {
        require(loadState.value is LoadState.Loaded)
        selectedFilters.value = selectedFilters.value.copy(price = price)
    }

    fun updateSize(size: Size) {
        require(loadState.value is LoadState.Loaded)
        selectedFilters.value = selectedFilters.value.copy(size = size)
    }

    fun updateCategory(category: Category) {
        require(loadState.value is LoadState.Loaded)
        selectedCategory.value = category
    }

    fun refreshProducts() {
        require(loadState.value is LoadState.Loaded)
        viewModelScope.launch {
            exceptionHandler.handle {
                productsRefreshing.value = true
                homeRepository.refreshProducts(selectedFilters.value, selectedCategory.value)
                productsRefreshing.value = false
            }.catch<Exception> {
                productsRefreshing.value = false
                false
            }.execute()
        }
    }


    sealed class State {
        object Loading : State()
        class LoadingError(val error: StringDesc?) : State()
        data class Loaded(
            val isProductsRefreshing: Boolean,

            val location: String,
            val selectedFilters: Filters,
            val selectedCategory: Category?,
            val categories: List<Category>,
            val products: Products,
            val favourites: List<Int>,
            val cartProductsAmount: Int
        ) : State()
    }

    private sealed class LoadState {
        object NotExecuted : LoadState()
        object Loading : LoadState()
        class LoadingError(val error: StringDesc) : LoadState()
        object Loaded : LoadState()
    }
}