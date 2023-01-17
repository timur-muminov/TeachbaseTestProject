package com.teachbaseTestProject.home.di

import com.teachbaseTestProject.home.presentation.Categories
import com.teachbaseTestProject.home.presentation.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeFeatureModule = module {
    viewModel {
        HomeViewModel(exceptionHandler = get(), byFiltersSearchable = get())
    }
    single { Categories() }
}