package com.ecommerceconcept.app_dependencies.repository

import com.ecommerceconcept.app_dependencies.R
import com.ecommerceconcept.app_dependencies.remote.api.DataAPI
import com.ecommerceconcept.app_dependencies.remote.entity.home.BestSellerDTO
import com.ecommerceconcept.app_dependencies.remote.entity.home.HotSalesDTO
import com.ecommerceconcept.app_dependencies.remote.entity.home.ProductsDTO
import com.ecommerceconcept.app_dependencies.exceptions_handler.NetworkExceptionMapper
import com.ecommerceconcept.categories.Category
import com.ecommerceconcept.filters.Filters
import com.ecommerceconcept.home.model.HomeRepository
import com.ecommerceconcept.products.BestSellerProduct
import com.ecommerceconcept.products.HotSalesProduct
import com.ecommerceconcept.products.Products
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext

class HomeRepositoryImpl(private val dataAPI: DataAPI, private val networkExceptionMapper: NetworkExceptionMapper) :
    HomeRepository {

    private val productsMutableStateFlow = MutableStateFlow<Products?>(null)
    override fun productsFlow(): Flow<Products?> = productsMutableStateFlow

    override suspend fun refreshProducts(filters: Filters?, category: Category?) = withContext(Dispatchers.IO){
        productsMutableStateFlow.value = networkExceptionMapper.handle { dataAPI.getProducts() }.mapToProducts()
    }

    private val categoryMutableStateFlow = MutableStateFlow<List<Category>?>(null)
    override fun categoriesFlow(): Flow<List<Category>?> = categoryMutableStateFlow

    override suspend fun refreshCategories() {
        categoryMutableStateFlow.value = getTestCategories()
    }

    private fun getTestCategories() = listOf(
        Category(0, "Phones", R.drawable.disabled_phones_category),
        Category(1, "Computers", R.drawable.disabled_computers_categories),
        Category(2, "Health", R.drawable.disabled_health_categories),
        Category(3, "Books", R.drawable.disabled_books_categories)
    )
}

private fun ProductsDTO.mapToProducts() = Products(
    bestSellerProducts = best_seller.mapToBestSellers(), hotSaleProducts = home_store.mapToHotSales()
)

private fun List<BestSellerDTO>.mapToBestSellers() = map {
    BestSellerProduct(
        id = it.id,
        isFavourites = it.is_favorites,
        priceWithoutDiscount = it.price_without_discount,
        discountPrice = it.discount_price,
        picture = it.picture,
        title = it.title
    )
}

private fun List<HotSalesDTO>.mapToHotSales() = map {
    HotSalesProduct(
        id = it.id,
        isNew = it.is_new,
        title = it.title,
        subtitle = it.subtitle,
        picture = it.picture,
        isBuy = it.is_buy
    )
}