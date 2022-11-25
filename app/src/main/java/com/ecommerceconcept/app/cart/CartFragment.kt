package com.ecommerceconcept.app.cart

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.ecommerceconcept.app.core.BaseFragment
import com.ecommerceconcept.app.databinding.CartFragmentBinding
import com.ecommerceconcept.app.utils.launchWith
import com.ecommerceconcept.app.utils.onEachChanged
import com.ecommerceconcept.app.databinding.CartItemBinding
import com.ecommerceconcept.app.utils.epicadapter.EpicAdapter
import com.ecommerceconcept.app.utils.epicadapter.requireEpicAdapter
import com.ecommerceconcept.app.utils.toPriceFormat
import com.ecommerceconcept.entitiy.cart.CartProduct
import com.ecommerceconcept.cart.presentation.CartViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import org.koin.androidx.viewmodel.ext.android.viewModel

class CartFragment : BaseFragment<CartFragmentBinding>(CartFragmentBinding::inflate) {

    private val viewModel: CartViewModel by viewModel()


    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        with(binding) {
            backButton.setOnClickListener {
                findNavController().popBackStack()
            }
            addAddressButton.setOnClickListener {
                showOnSnackBar("add address button")
            }

            checkoutButton.setOnClickListener {
                showOnSnackBar("Checkout button")
            }

            cartRecyclerview.adapter = buildCartAdapter()
        }

        viewModel.state.map {
            it.products
        }.onEachChanged {
            binding.cartRecyclerview.requireEpicAdapter().loadItems(it.keys.toList())
        }.launchWith(viewLifecycleOwner)


        viewModel.state.onEachChanged {
            binding.totalPriceTextview.text = it.totalPrice.toPriceFormat(" us")
            binding.deliveryTypeTextview.text = it.delivery
        }.launchWith(viewLifecycleOwner)

    }

    private fun buildCartAdapter() = EpicAdapter {
        setup<CartProduct, CartItemBinding>(CartItemBinding::inflate) {

            init {
                productImageview.clipToOutline = true
            }

            bind { scope, _, item ->
                Glide.with(requireContext()).load(item.image).into(productImageview)
                productNameTextview.text = item.title
                productPriceTextview.text = item.price.toPriceFormat()

                trashCanImageview.setOnClickListener {
                    viewModel.remove(item)
                }

                plusImageview.setOnClickListener {
                    viewModel.plusOne(item)
                }

                minusImageview.setOnClickListener {
                    viewModel.minusOne(item)
                }

                viewModel.state.map {
                    it.products
                }.onEachChanged {
                    if(it[item] == 0) viewModel.remove(item)
                    productAmountTextview.text = it.getOrElse(item) { 0 }.toString()
                }.launchIn(scope)
            }
        }
    }

    private fun showOnSnackBar(text: String) = Snackbar.make(binding.root, text, Snackbar.LENGTH_SHORT).show()
}