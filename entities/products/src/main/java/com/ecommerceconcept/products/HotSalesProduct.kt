package com.ecommerceconcept.products

data class HotSalesProduct(
    val id: Int,
    val isNew: Boolean?,
    val title: String,
    val subtitle: String,
    val picture: String,
    val isBuy: Boolean
)