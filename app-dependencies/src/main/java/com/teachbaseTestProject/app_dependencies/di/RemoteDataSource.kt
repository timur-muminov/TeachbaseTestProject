package com.teachbaseTestProject.app_dependencies.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.teachbaseTestProject.app_dependencies.remote.api.RemoteMoviesAPI
import com.teachbaseTestProject.app_dependencies.remote.data_sources.RemoteDataSourceImpl
import com.teachbaseTestProject.app_dependencies.remote.request_builder.RequestBuilder
import com.teachbaseTestProject.app_dependencies.remote.request_builder.RequestBuilderImpl
import com.teachbaseTestProject.app_dependencies.repository.model.RemoteDataSource
import com.teachbaseTestProject.app_dependencies.token_storage.TokenStorage
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

    single<RemoteDataSource> {
        RemoteDataSourceImpl(
            requestBuilder = get(),
            exceptionMapper = get(),
            remoteMoviesAPI = get()
        )
    }
    single<RequestBuilder> { RequestBuilderImpl(tokenStorage = get()) }
    single { TokenStorage(androidApplication()) }


    single<RemoteMoviesAPI> {
        get<Retrofit>().create(RemoteMoviesAPI::class.java)
    }

    factory<Retrofit> {
        Retrofit.Builder().baseUrl("https://api.kinopoisk.dev/").client(
            get()
        ).addConverterFactory(GsonConverterFactory.create(get())).build()
    }

    factory<OkHttpClient> {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                this.level = HttpLoggingInterceptor.Level.HEADERS
            }).cache(get()).build()
    }

    factory<Gson> {
        GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
    }

    factory<Cache> {
        Cache(androidApplication().cacheDir, (50 * 1024 * 1024).toLong())
    }
}
