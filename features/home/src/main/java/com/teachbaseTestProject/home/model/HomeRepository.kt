package com.teachbaseTestProject.home.model

import com.teachbaseTestProject.entities.movie.Category
import com.teachbaseTestProject.entities.movie.Movie
import kotlinx.coroutines.flow.Flow


interface HomeRepository {
    fun categoriesFlow(): Flow<List<Category>>
    suspend fun refresh()
    fun moviesFlowByCategory(category: Category): Flow<List<Movie>>
}