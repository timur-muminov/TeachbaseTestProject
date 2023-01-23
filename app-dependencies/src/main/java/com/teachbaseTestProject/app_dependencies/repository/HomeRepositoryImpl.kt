package com.teachbaseTestProject.app_dependencies.repository

import com.teachbaseTestProject.app_dependencies.repository.model.LocalDataSource
import com.teachbaseTestProject.app_dependencies.repository.model.RemoteDataSource
import com.teachbaseTestProject.home.model.HomeRepository
import com.teachbaseTestProject.home.presentation.Categories
import com.teachbaseTestProject.entities.movie.Category
import com.teachbaseTestProject.entities.movie.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf


class HomeRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val categories: Categories
) : HomeRepository {

    override fun categoriesFlow(): Flow<List<Category>> = flowOf(categories.categories)

    override suspend fun refresh() {
     /*   categories.categoriesToFilters.values.forEach { filter ->
            val result = remoteDataSource.getMoviesByFilters(filter, 1)
            localDataSource.updateMovies(filter, result)
        }*/
    }

    override fun moviesFlowByCategory(category: Category): Flow<List<Movie>> =
        localDataSource.getMoviesByFilters(categories.categoriesToFilters[category]!!)

}