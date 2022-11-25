package com.ecommerceconcept.app_dependencies.remote.entity.home

import com.google.gson.annotations.Expose

data class HotSalesDTO(
    @Expose val id: Int,
    @Expose val is_new: Boolean?,
    @Expose val title: String,
    @Expose val subtitle: String,
    @Expose val picture: String,
    @Expose val is_buy: Boolean
)