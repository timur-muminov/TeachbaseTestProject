package com.teachbaseTestProject.search.di

import com.teachbaseTestProject.search.presentation.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val searchFeatureModule = module {
    viewModel {
        SearchViewModel(exceptionHandler = get(), byNameSearchable = get())
    }
}