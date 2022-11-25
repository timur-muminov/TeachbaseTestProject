package com.ecommerceconcept.product

data class ProductDetail(
    val id: Int,
    val cpu: String,
    val camera: String,
    val capacity: List<String>,
    val color: List<String>,
    val images: List<String>,
    val price: String,
    val rating: Float,
    val sd: String,
    val ssd: String,
    val title: String
)
