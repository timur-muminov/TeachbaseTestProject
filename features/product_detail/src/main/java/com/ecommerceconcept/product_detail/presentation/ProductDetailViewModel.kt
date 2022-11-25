package com.ecommerceconcept.product_detail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecommerceconcept.favourites.model.FavouritesRepository
import com.ecommerceconcept.my_cart.model.CartRepository
import com.ecommerceconcept.product.ProductDetail
import com.ecommerceconcept.product_detail.model.ProductDetailRepository
import dev.icerock.moko.errors.handler.ExceptionHandler
import dev.icerock.moko.errors.mappers.mapThrowable
import dev.icerock.moko.resources.desc.StringDesc
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProductDetailViewModel(
    private val productId: Int,
    private val productDetailRepository: ProductDetailRepository,
    private val exceptionHandler: ExceptionHandler,
    private val cartRepository: CartRepository,
    private val favouritesRepository: FavouritesRepository
) : ViewModel() {

    private val loadState = MutableStateFlow<LoadState>(LoadState.NotExecuted)

    private val selectedColor = MutableStateFlow<String?>(null)
    private val selectedCapacity = MutableStateFlow<String?>(null)


    val state = combine(
        loadState,
        selectedColor,
        selectedCapacity,
        productDetailRepository.productDetailFlow(),
        favouritesRepository.favouritesFlow()
    ) { load, selectedColor, selectedCapacity, productDetail, favourites ->
        when (load) {
            is LoadState.Loading -> State.Loading
            is LoadState.LoadingError -> State.LoadingError(load.error)
            is LoadState.NotExecuted -> State.Loading
            is LoadState.Loaded -> State.Loaded(
                productDetail!!,
                selectedColor ?: productDetail.color[0],
                selectedCapacity ?: productDetail.capacity[0],
                favourites
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
                productDetailRepository.refreshProductDetail(productId)
                loadState.value = LoadState.Loaded
            }.catch<Exception> { error ->
                loadState.value = LoadState.LoadingError(error.mapThrowable())
                true
            }.execute()
        }
    }

    fun toggleFavourite() {
        val loadedState = requireNotNull(state.value as? State.Loaded)
        favouritesRepository.toggleFavourite(loadedState.productDetail.id)
    }

    fun isFavourite(): Boolean {
        val loadedState = requireNotNull(state.value as? State.Loaded)
        return favouritesRepository.isFavourite(loadedState.productDetail.id)
    }

    fun addToCart() {
        val loadedState = requireNotNull(state.value as? State.Loaded)
        // loadedState.productDetail.id - this entry should have been here,
        // but due to the peculiarities of the backend and for better work, it is static here
        cartRepository.plusOne(1)
    }

    fun updateColor(color: String) {
        require(loadState.value is LoadState.Loaded)
        selectedColor.value = color
    }

    fun updateCapacity(capacity: String) {
        require(loadState.value is LoadState.Loaded)
        selectedCapacity.value = capacity
    }

    sealed class State {
        object Loading : State()
        class LoadingError(val error: StringDesc?) : State()
        data class Loaded(
            val productDetail: ProductDetail,
            val selectedColor: String,
            val selectedCapacity: String,
            val favourites: List<Int>
        ) : State()
    }

    private sealed class LoadState {
        object NotExecuted : LoadState()
        object Loading : LoadState()
        class LoadingError(val error: StringDesc) : LoadState()
        object Loaded : LoadState()
    }
}