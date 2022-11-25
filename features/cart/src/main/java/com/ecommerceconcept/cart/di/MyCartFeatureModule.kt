package com.ecommerceconcept.cart.di

import com.ecommerceconcept.cart.presentation.CartViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val myCartFeatureModule = module {
    viewModel { CartViewModel(cartRepository = get()) }
}