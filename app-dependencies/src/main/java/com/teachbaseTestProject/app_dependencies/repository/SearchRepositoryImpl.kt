package com.teachbaseTestProject.app_dependencies.repository

import com.teachbaseTestProject.app_dependencies.remote.entity.movie.MovieDTO
import com.teachbaseTestProject.app_dependencies.remote.entity.movie.MoviesDTO
import com.teachbaseTestProject.app_dependencies.repository.model.LocalDataSource
import com.teachbaseTestProject.app_dependencies.repository.model.RemoteDataSource
import com.teachbaseTestProject.entities.filter.MovieFilter
import com.teachbaseTestProject.home.model.HomeRepository
import com.teachbaseTestProject.home.presentation.Categories
import com.teachbaseTestProject.entities.movie.Category
import com.teachbaseTestProject.entities.movie.Movie
import com.teachbaseTestProject.search.model.SearchRepository
import kotlinx.coroutines.flow.*


class SearchRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : SearchRepository {

    override suspend fun getMoviesByName(movieName: String, page: Int): List<Movie> {
        return remoteDataSource.getMoviesByName(movieName, page).toMovies()
    }
}

private fun MoviesDTO.toMovies() = docs.map { it.toMovie() }
private fun MovieDTO.toMovie() =
    Movie(
        id = id,
        name = name,
        secondName = alternativeName,
        imageUrl = poster?.previewUrl,
        genre = type,
        rate = rating?.kp,
        votes = votes?.kp,
    )