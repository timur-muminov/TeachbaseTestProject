package com.ecommerceconcept.app_dependencies.remote.entity.home

import com.google.gson.annotations.Expose

data class BestSellerDTO(
    @Expose val id: Int,
    @Expose val is_favorites: Boolean,
    @Expose val price_without_discount: String,
    @Expose val discount_price: String,
    @Expose val picture: String,
    @Expose val title: String
)