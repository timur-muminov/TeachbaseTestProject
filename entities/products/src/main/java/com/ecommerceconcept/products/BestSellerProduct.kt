package com.ecommerceconcept.products

data class BestSellerProduct(
    val id: Int,
    val isFavourites: Boolean,
    val priceWithoutDiscount: String,
    val discountPrice: String,
    val picture: String,
    val title: String
)