package com.teachbaseTestProject.app_dependencies.repository.model

import com.teachbaseTestProject.app_dependencies.remote.entity.movie.MoviesDTO
import com.teachbaseTestProject.app_dependencies.remote.entity.movie_detail.MovieDetailDTO
import com.teachbaseTestProject.entities.filter.MovieFilter

interface RemoteDataSource {
    suspend fun getMoviesByFilters(movieFilter: MovieFilter, page: Int): MoviesDTO
    suspend fun getMovieById(movieId: Int): MovieDetailDTO
    suspend fun getMoviesByName(movieName: String, page: Int): MoviesDTO
}