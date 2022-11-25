package com.ecommerceconcept.app_dependencies.remote.entity.cart

import com.google.gson.annotations.Expose

data class CartDTO(
    @Expose val id: Int,
    @Expose val basket: List<CartProductDTO>,
    @Expose val delivery: String,
    @Expose val total: String
)
