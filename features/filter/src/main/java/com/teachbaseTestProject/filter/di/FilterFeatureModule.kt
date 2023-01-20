package com.teachbaseTestProject.filter.di

import com.teachbaseTestProject.filter.presentation.FilterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val filterFeatureModule = module {
    viewModel { FilterViewModel() }
}