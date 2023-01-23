package com.teachbaseTestProject.filter.presentation

import com.teachbaseTestProject.common.utils.pagination.Pagination
import com.teachbaseTestProject.entities.filter.MovieFilter
import com.teachbaseTestProject.entities.movie.Movie
import com.teachbaseTestProject.filter.model.FilterRepository
import dev.icerock.moko.errors.handler.ExceptionHandler
import dev.icerock.moko.errors.mappers.mapThrowable
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.resources.desc.StringDesc
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FilterResultViewModel(
    private val movieFilter: MovieFilter,
    private val exceptionHandler: ExceptionHandler,
    private val filterRepository: FilterRepository
) : ViewModel() {

    private val moviesPagination = Pagination<Movie, MovieFilter>(1) { movieFilter, page ->
        filterRepository.getMoviesByFilters(movieFilter, page)
    }

    private val privateLoadStateMutableStateFlow = MutableStateFlow<PrivateLoadState>(PrivateLoadState.NotExecuted)

    val state = combine(
        privateLoadStateMutableStateFlow,
        moviesPagination.dataFlow()
    ) { privateState, searchMovies ->
        when (privateState) {
            is PrivateLoadState.NotExecuted -> ViewLoadState.Loading
            is PrivateLoadState.Loaded -> ViewLoadState.Loaded(searchMovies)
            is PrivateLoadState.Loading -> ViewLoadState.Loading
            is PrivateLoadState.LoadingError -> ViewLoadState.LoadingError(privateState.error)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), ViewLoadState.Loading)

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            exceptionHandler.handle {
                privateLoadStateMutableStateFlow.value = PrivateLoadState.Loading
                moviesPagination.next(movieFilter)
                privateLoadStateMutableStateFlow.value = PrivateLoadState.Loaded
            }.catch<Exception> { error ->
                privateLoadStateMutableStateFlow.value = PrivateLoadState.LoadingError(error.mapThrowable())
                true
            }.execute()
        }
    }

    sealed interface ViewLoadState {
        object Loading : ViewLoadState
        data class LoadingError(val error: StringDesc?) : ViewLoadState
        data class Loaded(val movies: List<Movie>) : ViewLoadState
    }

    private sealed interface PrivateLoadState {
        object Loading : PrivateLoadState
        data class LoadingError(val error: StringDesc) : PrivateLoadState
        object Loaded : PrivateLoadState
        object NotExecuted : PrivateLoadState
    }
}