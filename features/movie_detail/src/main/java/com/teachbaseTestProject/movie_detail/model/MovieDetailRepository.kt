package com.teachbaseTestProject.movie_detail.model

import com.teachbaseTestProject.entities.movie.MovieDetail
import kotlinx.coroutines.flow.Flow

interface MovieDetailRepository {
    suspend fun movieDetailFlow(movieId: Int): Flow<MovieDetail?>
    suspend fun refreshMovieDetailById(movieId: Int)
}