package com.ecommerceconcept.app_dependencies.repository

import com.ecommerceconcept.app_dependencies.remote.api.DataAPI
import com.ecommerceconcept.app_dependencies.remote.entity.cart.CartDTO
import com.ecommerceconcept.app_dependencies.remote.entity.cart.CartProductDTO
import com.ecommerceconcept.app_dependencies.exceptions_handler.NetworkExceptionMapper
import com.ecommerceconcept.entitiy.cart.Cart
import com.ecommerceconcept.entitiy.cart.CartProduct
import com.ecommerceconcept.my_cart.model.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class CartRepositoryImpl(private val dataAPI: DataAPI, private val networkExceptionMapper: NetworkExceptionMapper) :
    CartRepository {

    private val storageMutableStateFlow = MutableStateFlow<List<CartProduct>>(emptyList())

    private val cartMutableStateFlow = MutableStateFlow<Cart?>(null)
    override fun cartFlow(): Flow<Cart?> = cartMutableStateFlow


    override suspend fun refreshCartFlow() {
        val result = networkExceptionMapper.handle { dataAPI.getCart() }.toCart()
        cartMutableStateFlow.value = result
        storageMutableStateFlow.value = result.products.keys.toList()
    }

    override fun getCurrentAmount(productId: Int): Int? {
        val key = cartMutableStateFlow.value?.products?.keys?.firstOrNull { it.id == productId }
        return cartMutableStateFlow.value?.products?.get(key)
    }


    private fun addToCart(productId: Int) {
        cartMutableStateFlow.update { cart ->
            cart!!.copy(products = cart.products.toMutableMap().also {
                it[storageMutableStateFlow.value.first { item -> item.id == productId }] = 1
            })
        }
    }

    override fun setAmount(productId: Int, amount: Int) {
        cartMutableStateFlow.update { cart ->
            cart!!.copy(products = cart.products.toMutableMap().also {
                val key = it.keys.firstOrNull { product -> product.id == productId } ?: return@also
                if (amount > 0) it[key] = amount else it[key] = 0
            })
        }
    }


    override fun plusOne(productId: Int) {
        val currentAmount = getCurrentAmount(productId)
        if (currentAmount == null) {
            addToCart(productId)
        } else
            setAmount(productId, currentAmount + 1)
    }

    override fun minusOne(productId: Int) {
        val currentAmount = getCurrentAmount(productId) ?: return
        setAmount(productId, currentAmount - 1)
    }

    override fun remove(productId: Int) {
        cartMutableStateFlow.update { cart ->
            cart!!.copy(products = cart.products.filterNot { it.key.id == productId })
        }
    }
}

private fun CartDTO.toCart() = Cart(
    id = id,
    products = basket.toCartProducts(1),
    delivery = delivery,
    totalPrice = total
)

private fun List<CartProductDTO>.toCartProducts(amount: Int) = associate {
    CartProduct(
        id = it.id,
        image = it.images,
        price = it.price,
        title = it.title
    ) to amount
}