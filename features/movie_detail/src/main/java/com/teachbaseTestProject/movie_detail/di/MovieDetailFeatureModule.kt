package com.teachbaseTestProject.movie_detail.di

import com.teachbaseTestProject.movie_detail.presentation.MovieDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val movieDetailFeatureModule = module {
    viewModel { parameters ->
        MovieDetailViewModel(movieId = parameters.get(), movieDetailRepository = get(), exceptionHandler = get())
    }
}