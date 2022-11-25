package com.ecommerceconcept.app_dependencies.remote.entity.home

import com.google.gson.annotations.Expose

data class ProductsDTO(
    @Expose val home_store: List<HotSalesDTO>,
    @Expose val best_seller: List<BestSellerDTO>
)
