package com.teachbaseTestProject.home.model

import com.teachbaseTestProject.movie.Category
import com.teachbaseTestProject.movie.Movie
import kotlinx.coroutines.flow.Flow


interface HomeRepository {
    fun categoriesFlow(): Flow<List<Category>>
    suspend fun refresh()
    fun moviesFlowByCategory(category: Category): Flow<List<Movie>>
}