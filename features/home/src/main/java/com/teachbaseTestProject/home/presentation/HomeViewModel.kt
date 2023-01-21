@file:OptIn(ExperimentalCoroutinesApi::class)

package com.teachbaseTestProject.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teachbaseTestProject.home.model.HomeRepository
import com.teachbaseTestProject.entities.movie.Category
import com.teachbaseTestProject.entities.movie.Movie
import dev.icerock.moko.errors.handler.ExceptionHandler
import dev.icerock.moko.errors.mappers.mapThrowable
import dev.icerock.moko.resources.desc.StringDesc
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(
    private val exceptionHandler: ExceptionHandler,
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val privateLoadState = MutableStateFlow<PrivateLoadState>(PrivateLoadState.NotExecuted)

    val state = combine(
        privateLoadState,
        itemsFlow()
    ) { privateState, items ->
        when (privateState) {
            is PrivateLoadState.Loaded -> ViewLoadState.Loaded(items)
            is PrivateLoadState.Loading -> ViewLoadState.Loading
            is PrivateLoadState.LoadingError -> ViewLoadState.LoadingError(privateState.error)
            is PrivateLoadState.NotExecuted -> ViewLoadState.Loading
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), ViewLoadState.Loading)

    private fun itemsFlow() = homeRepository.categoriesFlow().flatMapLatest { categories ->
        combine(
            categories.map(homeRepository::moviesFlowByCategory)
        ) { movies ->
            categories.mapIndexed { index, category ->
                CategoryItem(category, movies[index])
            }
        }
    }

    init {
        load()
    }

    fun load() {
        if (privateLoadState.value == PrivateLoadState.Loading) return

        viewModelScope.launch {
            exceptionHandler.handle {
                privateLoadState.value = PrivateLoadState.Loading
                homeRepository.refresh()
                privateLoadState.value = PrivateLoadState.Loaded
            }.catch<Exception> { error ->
                privateLoadState.value = PrivateLoadState.LoadingError(error.mapThrowable())
                true
            }.execute()
        }
    }


    data class CategoryItem(val category: Category, val movies: List<Movie>)

    sealed interface ViewLoadState {
        object Loading : ViewLoadState
        data class LoadingError(val error: StringDesc?) : ViewLoadState
        data class Loaded(val items: List<CategoryItem>) : ViewLoadState
    }

    private sealed interface PrivateLoadState {
        object Loading : PrivateLoadState
        data class LoadingError(val error: StringDesc) : PrivateLoadState
        object Loaded : PrivateLoadState
        object NotExecuted : PrivateLoadState
    }
}