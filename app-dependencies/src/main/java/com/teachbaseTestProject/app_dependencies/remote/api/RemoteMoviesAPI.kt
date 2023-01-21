package com.teachbaseTestProject.app_dependencies.remote.api

import com.teachbaseTestProject.app_dependencies.remote.entity.movie.MoviesDTO
import com.teachbaseTestProject.app_dependencies.remote.entity.movie_detail.MovieDetailDTO
import com.teachbaseTestProject.entities.movie.Movie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface RemoteMoviesAPI {

    @GET
    suspend fun searchMovie(@Url request: String): Response<MoviesDTO>

    @GET
    suspend fun searchMovieDetail(@Url request: String): Response<MovieDetailDTO>
}