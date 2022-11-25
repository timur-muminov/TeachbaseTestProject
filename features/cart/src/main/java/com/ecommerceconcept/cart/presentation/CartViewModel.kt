package com.ecommerceconcept.cart.presentation

import androidx.lifecycle.ViewModel
import com.ecommerceconcept.entitiy.cart.CartProduct
import com.ecommerceconcept.my_cart.model.CartRepository
import kotlinx.coroutines.flow.filterNotNull


class CartViewModel(
    private val cartRepository: CartRepository
) : ViewModel() {

    val state = cartRepository.cartFlow().filterNotNull()

    fun plusOne(cartProduct: CartProduct) {
        cartRepository.plusOne(cartProduct.id)
    }

    fun minusOne(cartProduct: CartProduct) {
        cartRepository.minusOne(cartProduct.id)
    }

    fun remove(cartProduct: CartProduct) {
        cartRepository.remove(cartProduct.id)
    }
}