package com.teachbaseTestProject.filter.model

import com.teachbaseTestProject.filter.MovieFilter
import com.teachbaseTestProject.movie.Movie

interface FilterRepository {
    suspend fun getMoviesByFilters(movieFilter: MovieFilter, page: Int): List<Movie>
}