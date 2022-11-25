package com.ecommerceconcept.app

import android.app.Application
import com.ecommerceconcept.app_dependencies.di.exceptionHandlerModule
import com.ecommerceconcept.app_dependencies.di.networkModule
import com.ecommerceconcept.app_dependencies.di.repositoryModule
import com.ecommerceconcept.app_dependencies.exceptions_handler.ExceptionHandlerConfigurator
import com.ecommerceconcept.home.di.homeFeatureModule
import com.ecommerceconcept.cart.di.myCartFeatureModule
import com.ecommerceconcept.product_detail.di.productDetailFeatureModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        ExceptionHandlerConfigurator.initialize()
        startKoin {
            androidContext(this@App)
            modules(
                repositoryModule, exceptionHandlerModule, networkModule, homeFeatureModule, productDetailFeatureModule,
                myCartFeatureModule
            )
        }
    }
}