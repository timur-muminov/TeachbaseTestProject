package com.teachbaseTestProject.search.model

import com.teachbaseTestProject.entities.movie.Movie

interface SearchRepository {
    suspend fun getMoviesByName(movieName: String, page:Int): List<Movie>
}