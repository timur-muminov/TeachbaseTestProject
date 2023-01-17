package com.teachbaseTestProject.app_dependencies.repository.model

import com.teachbaseTestProject.app_dependencies.local.entity.movie_detail.LocalMovieDetailDTO
import com.teachbaseTestProject.app_dependencies.remote.entity.movie.MoviesDTO
import com.teachbaseTestProject.filter.MovieFilter
import com.teachbaseTestProject.movie.Category
import com.teachbaseTestProject.movie.Movie
import com.teachbaseTestProject.movie.MovieDetail
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun getMoviesByFilters(movieFilter: MovieFilter): Flow<List<Movie>>
    fun getMovieDetailByRemoteId(remoteId: Int): Flow<MovieDetail?>
    suspend fun updateMovies(movieFilter: MovieFilter, moviesDTO: MoviesDTO)
    suspend fun updateMovieDetail(localMovieDetailDTO: LocalMovieDetailDTO)
}