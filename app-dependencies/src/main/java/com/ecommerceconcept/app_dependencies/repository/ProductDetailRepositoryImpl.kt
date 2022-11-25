package com.ecommerceconcept.app_dependencies.repository

import com.ecommerceconcept.app_dependencies.remote.api.DataAPI
import com.ecommerceconcept.app_dependencies.remote.entity.product_detail.ProductDetailDTO
import com.ecommerceconcept.app_dependencies.exceptions_handler.NetworkExceptionMapper
import com.ecommerceconcept.product.ProductDetail
import com.ecommerceconcept.product_detail.model.ProductDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext

class ProductDetailRepositoryImpl(
    private val dataAPI: DataAPI,
    private val networkExceptionMapper: NetworkExceptionMapper
) :
    ProductDetailRepository {

    private val productDetailMutableStateFlow = MutableStateFlow<ProductDetail?>(null)
    override fun productDetailFlow() = productDetailMutableStateFlow.asStateFlow()

    override suspend fun refreshProductDetail(productId: Int) = withContext(Dispatchers.IO){
        productDetailMutableStateFlow.value = networkExceptionMapper.handle { dataAPI.getProductDetail() }.toProductDetail()
    }
}

fun ProductDetailDTO.toProductDetail() = ProductDetail(
    id = id,
    cpu = CPU,
    camera = camera,
    capacity = capacity,
    color = color,
    images = images,
    price = price,
    rating = rating,
    sd = sd,
    ssd = ssd,
    title = title
)