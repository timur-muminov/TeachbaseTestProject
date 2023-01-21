package com.teachbaseTestProject.app_dependencies.di

import com.teachbaseTestProject.app_dependencies.exceptions_handler.NetworkExceptionMapper
import com.teachbaseTestProject.app_dependencies.repository.FilterRepositoryImpl
import com.teachbaseTestProject.app_dependencies.repository.HomeRepositoryImpl
import com.teachbaseTestProject.app_dependencies.repository.MovieDetailRepositoryImpl
import com.teachbaseTestProject.app_dependencies.repository.SearchRepositoryImpl
import com.teachbaseTestProject.filter.model.FilterRepository
import com.teachbaseTestProject.home.model.HomeRepository
import com.teachbaseTestProject.movie_detail.model.MovieDetailRepository
import com.teachbaseTestProject.search.model.SearchRepository
import dev.icerock.moko.errors.handler.ExceptionHandler
import dev.icerock.moko.errors.mappers.ExceptionMappersStorage
import dev.icerock.moko.errors.presenters.SnackBarDuration
import dev.icerock.moko.errors.presenters.SnackBarErrorPresenter
import dev.icerock.moko.resources.desc.StringDesc
import org.koin.dsl.module


val exceptionHandlerModule = module {
    single { NetworkExceptionMapper() }
    single<ExceptionHandler> {
        ExceptionHandler<StringDesc>(
            exceptionMapper = ExceptionMappersStorage.throwableMapper(),
            errorPresenter = SnackBarErrorPresenter(duration = SnackBarDuration.SHORT)
        )
    }
}


val repositoryModule = module {
    single<HomeRepository> { HomeRepositoryImpl(localDataSource = get(), remoteDataSource = get(), categories = get()) }
    single<MovieDetailRepository> { MovieDetailRepositoryImpl(localDataSource = get(), remoteDataSource = get()) }
    single<SearchRepository> { SearchRepositoryImpl(remoteDataSource = get()) }
    single<FilterRepository> { FilterRepositoryImpl(remoteDataSource = get()) }
}

