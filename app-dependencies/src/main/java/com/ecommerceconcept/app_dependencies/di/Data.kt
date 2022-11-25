package com.ecommerceconcept.app_dependencies.di

import com.ecommerceconcept.app_dependencies.repository.*
import com.ecommerceconcept.app_dependencies.exceptions_handler.NetworkExceptionMapper
import com.ecommerceconcept.favourites.model.FavouritesRepository
import com.ecommerceconcept.home.model.GeoApi
import com.ecommerceconcept.home.model.HomeRepository
import com.ecommerceconcept.my_cart.model.CartRepository
import com.ecommerceconcept.product_detail.model.ProductDetailRepository
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
    single<HomeRepository> { HomeRepositoryImpl(dataAPI = get(), networkExceptionMapper = get()) }
    single<ProductDetailRepository> { ProductDetailRepositoryImpl(dataAPI = get(), networkExceptionMapper = get()) }

    single<CartRepository> { CartRepositoryImpl(dataAPI = get(), networkExceptionMapper = get()) }
    single <FavouritesRepository> { FavouritesRepositoryImpl() }
    single<GeoApi> { GeoApiImpl() }
}

