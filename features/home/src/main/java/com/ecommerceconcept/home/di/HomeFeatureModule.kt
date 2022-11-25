package com.ecommerceconcept.home.di

import com.ecommerceconcept.home.presentation.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeFeatureModule = module {
    viewModel {
        HomeViewModel(
            homeRepository = get(),
            exceptionHandler = get(),
            geoApi = get(),
            cartRepository = get(),
            favouritesRepository = get()
        )
    }
}