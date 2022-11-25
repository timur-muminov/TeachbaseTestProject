package com.ecommerceconcept.app_dependencies.di

import com.ecommerceconcept.app_dependencies.remote.api.DataAPI
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

    single<DataAPI> {
        get<Retrofit>().create(DataAPI::class.java)
    }

    factory<Retrofit> {
        Retrofit.Builder().baseUrl("https://run.mocky.io/v3/").client(get()
        ).addConverterFactory(GsonConverterFactory.create(get())).build()
    }

    factory<OkHttpClient> {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                this.level = HttpLoggingInterceptor.Level.BODY
            }).cache(get()).build()
    }

    factory<Gson> {
        GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
    }

    factory<Cache> {
        Cache(androidApplication().cacheDir, (50 * 1024 * 1024).toLong())
    }
}
