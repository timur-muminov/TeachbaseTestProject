package com.teachbaseTestProject.filter.model

import com.teachbaseTestProject.entities.filter.MovieFilter
import com.teachbaseTestProject.entities.movie.Movie

interface FilterRepository {
    suspend fun getMoviesByFilters(movieFilter: MovieFilter, page: Int): List<Movie>
}