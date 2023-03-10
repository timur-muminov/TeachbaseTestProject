package com.teachbaseTestProject.app_dependencies.repository.model

import com.teachbaseTestProject.app_dependencies.local.entity.movie_detail.LocalMovieDetailDTO
import com.teachbaseTestProject.app_dependencies.remote.entity.movie.MoviesDTO
import com.teachbaseTestProject.entities.filter.MovieFilter
import com.teachbaseTestProject.entities.movie.Category
import com.teachbaseTestProject.entities.movie.Movie
import com.teachbaseTestProject.entities.movie.MovieDetail
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun getMoviesByFilters(movieFilter: MovieFilter): Flow<List<Movie>>
    fun getMovieDetailByRemoteId(remoteId: Int): Flow<MovieDetail?>
    suspend fun updateMovies(movieFilter: MovieFilter, moviesDTO: MoviesDTO)
    suspend fun updateMovieDetail(localMovieDetailDTO: LocalMovieDetailDTO)
}