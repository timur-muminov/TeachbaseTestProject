package com.ecommerceconcept.my_cart.model

import com.ecommerceconcept.entitiy.cart.Cart
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun cartFlow() : Flow<Cart?>
    suspend fun refreshCartFlow()
    fun getCurrentAmount(productId: Int): Int?
    fun setAmount(productId: Int, amount: Int)
    fun plusOne(productId: Int)
    fun minusOne(productId: Int)
    fun remove(productId: Int)
}