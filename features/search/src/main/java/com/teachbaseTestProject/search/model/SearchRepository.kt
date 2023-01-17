package com.teachbaseTestProject.search.model

import com.teachbaseTestProject.movie.Movie

interface SearchRepository {
    suspend fun getMoviesByName(movieName: String, page:Int): List<Movie>
}