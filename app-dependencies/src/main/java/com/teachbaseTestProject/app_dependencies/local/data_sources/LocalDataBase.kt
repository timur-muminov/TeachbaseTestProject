package com.teachbaseTestProject.app_dependencies.local.data_sources

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.teachbaseTestProject.app_dependencies.local.api.LocalMoviesApi
import com.teachbaseTestProject.app_dependencies.local.entity.movie.LocalMovieDTO
import com.teachbaseTestProject.app_dependencies.local.entity.movie_detail.LocalMovieDetailDTO
import com.teachbaseTestProject.app_dependencies.local.entity.movie_detail.PersonsConverter
import com.teachbaseTestProject.app_dependencies.local.entity.movie_detail.StringsConverter

@Database(entities = [LocalMovieDTO::class, LocalMovieDetailDTO::class], version = 1, exportSchema = false)
@TypeConverters(StringsConverter::class, PersonsConverter::class)
abstract class LocalDataBase : RoomDatabase() {
    abstract fun getLocalMoviesApi(): LocalMoviesApi
}