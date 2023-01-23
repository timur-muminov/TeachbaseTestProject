package com.teachbaseTestProject.app

import android.app.Application
import com.teachbaseTestProject.app_dependencies.di.exceptionHandlerModule
import com.teachbaseTestProject.app_dependencies.di.localDataSourceModule
import com.teachbaseTestProject.app_dependencies.di.networkModule
import com.teachbaseTestProject.app_dependencies.di.repositoryModule
import com.teachbaseTestProject.app_dependencies.exceptions_handler.ExceptionHandlerConfigurator
import com.teachbaseTestProject.filter.di.filterFeatureModule
import com.teachbaseTestProject.home.di.homeFeatureModule
import com.teachbaseTestProject.movie_detail.di.movieDetailFeatureModule
import com.teachbaseTestProject.search.di.searchFeatureModule

import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        ExceptionHandlerConfigurator.initialize()
        startKoin {
            androidContext(this@App)
            modules(
                repositoryModule,
                exceptionHandlerModule,
                networkModule,
                homeFeatureModule,
                localDataSourceModule,
                searchFeatureModule,
                filterFeatureModule,
                movieDetailFeatureModule
            )
        }
    }
}