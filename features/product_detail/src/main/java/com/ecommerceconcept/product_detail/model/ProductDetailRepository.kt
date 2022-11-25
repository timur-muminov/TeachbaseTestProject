package com.ecommerceconcept.product_detail.model

import com.ecommerceconcept.product.ProductDetail
import kotlinx.coroutines.flow.Flow


interface ProductDetailRepository {
    fun productDetailFlow() : Flow<ProductDetail?>
    suspend fun refreshProductDetail(productId: Int)
}