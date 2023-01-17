package com.teachbaseTestProject.app_dependencies.di

import androidx.room.Room
import com.teachbaseTestProject.app_dependencies.local.api.LocalMoviesApi
import com.teachbaseTestProject.app_dependencies.local.data_sources.LocalDataBase
import com.teachbaseTestProject.app_dependencies.local.data_sources.LocalDataSourceImpl
import com.teachbaseTestProject.app_dependencies.local.localId_builder.LocalIdBuilder
import com.teachbaseTestProject.app_dependencies.local.localId_builder.LocalIdBuilderImpl
import com.teachbaseTestProject.app_dependencies.repository.model.LocalDataSource
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localDataSourceModule = module {
    single<LocalDataBase> {
        Room.databaseBuilder(androidApplication(), LocalDataBase::class.java, "LocalDataBase").build()
    }
    single<LocalDataSource> { LocalDataSourceImpl(localMoviesApi = get(), localIdBuilder = get()) }
    single<LocalIdBuilder> { LocalIdBuilderImpl() }

    single<LocalMoviesApi> { get<LocalDataBase>().getLocalMoviesApi() }
}