package com.ecommerceconcept.home.model

import com.ecommerceconcept.categories.Category
import com.ecommerceconcept.filters.Filters
import com.ecommerceconcept.products.Products
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun productsFlow(): Flow<Products?>
    suspend fun refreshProducts(filters: Filters?, category: Category?)

    fun categoriesFlow(): Flow<List<Category>?>
    suspend fun refreshCategories()
}