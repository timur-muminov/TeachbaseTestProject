package com.ecommerceconcept.app_dependencies.remote.entity.cart

import com.google.gson.annotations.Expose

data class CartProductDTO(
    @Expose val id: Int,
    @Expose val images: String,
    @Expose val price: String,
    @Expose val title: String
)
