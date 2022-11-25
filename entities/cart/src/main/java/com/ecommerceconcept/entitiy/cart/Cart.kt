package com.ecommerceconcept.entitiy.cart

data class Cart(
    val id: Int,
    val products: Map<CartProduct, Int>,
    val delivery: String,
    val totalPrice: String
)
