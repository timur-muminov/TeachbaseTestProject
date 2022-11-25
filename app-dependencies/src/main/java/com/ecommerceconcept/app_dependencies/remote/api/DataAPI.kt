package com.ecommerceconcept.app_dependencies.remote.api

import com.ecommerceconcept.app_dependencies.remote.entity.cart.CartDTO
import com.ecommerceconcept.app_dependencies.remote.entity.home.ProductsDTO
import com.ecommerceconcept.app_dependencies.remote.entity.product_detail.ProductDetailDTO
import retrofit2.Response
import retrofit2.http.GET

interface DataAPI {

    @GET("654bd15e-b121-49ba-a588-960956b15175")
    suspend fun getProducts(): Response<ProductsDTO>


    @GET("6c14c560-15c6-4248-b9d2-b4508df7d4f5")
    suspend fun getProductDetail(): Response<ProductDetailDTO>


    @GET("53539a72-3c5f-4f30-bbb1-6ca10d42c149")
    suspend fun getCart(): Response<CartDTO>
}