package com.ecommerceconcept.app_dependencies.remote.entity.product_detail

import com.google.gson.annotations.Expose

data class ProductDetailDTO(
    @Expose val id: Int,
    @Expose val CPU: String,
    @Expose val camera: String,
    @Expose val capacity: List<String>,
    @Expose val color: List<String>,
    @Expose val images: List<String>,
    @Expose val price: String,
    @Expose val rating: Float,
    @Expose val sd: String,
    @Expose val ssd: String,
    @Expose val title: String
)
