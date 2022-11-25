package com.ecommerceconcept.filters

data class Filters(
    val search: String?,
    val brand: Brand?,
    val price: Price?,
    val size: Size?
)