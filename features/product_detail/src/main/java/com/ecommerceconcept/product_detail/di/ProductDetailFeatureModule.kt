package com.ecommerceconcept.product_detail.di

import com.ecommerceconcept.product_detail.presentation.ProductDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val productDetailFeatureModule = module {
    viewModel { parameters ->
        ProductDetailViewModel(
            productId = parameters.get(),
            productDetailRepository = get(),
            exceptionHandler = get(),
            cartRepository = get(),
            favouritesRepository = get()
        )
    }
}