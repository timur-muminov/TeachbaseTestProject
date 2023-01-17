package com.teachbaseTestProject.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teachbaseTestProject.common.utils.pagination.Pagination
import com.teachbaseTestProject.movie.Movie
import com.teachbaseTestProject.search.model.SearchRepository
import dev.icerock.moko.errors.handler.ExceptionHandler
import dev.icerock.moko.errors.mappers.mapThrowable
import dev.icerock.moko.resources.desc.StringDesc
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SearchViewModel(
    private val exceptionHandler: ExceptionHandler,
    private val searchRepository: SearchRepository
) : ViewModel() {

    private val moviesPagination = Pagination<Movie, String>(1) { movieName, page ->
        searchRepository.getMoviesByName(movieName, page)
    }

    private val privateLoadStateMutableStateFlow = MutableStateFlow<PrivateLoadState>(PrivateLoadState.Input)
    private val searchTextMutableStateFlow = MutableStateFlow<String?>(null)

    val state = combine(
        privateLoadStateMutableStateFlow,
        moviesPagination.dataFlow()
    ) { privateState, searchMovies ->
        when (privateState) {
            is PrivateLoadState.Input -> ViewLoadState.Input
            is PrivateLoadState.Loaded -> ViewLoadState.Loaded(searchMovies)
            is PrivateLoadState.Loading -> ViewLoadState.Loading
            is PrivateLoadState.LoadingError -> ViewLoadState.LoadingError(privateState.error)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), ViewLoadState.Input)

    fun searchMovie() {
        if (searchTextMutableStateFlow.value.isNullOrEmpty()) return
        viewModelScope.launch {
            exceptionHandler.handle {
                privateLoadStateMutableStateFlow.value = PrivateLoadState.Loading
                moviesPagination.next(searchTextMutableStateFlow.value!!)
                privateLoadStateMutableStateFlow.value = PrivateLoadState.Loaded
            }.catch<Exception> { error ->
                privateLoadStateMutableStateFlow.value = PrivateLoadState.LoadingError(error.mapThrowable())
                true
            }.execute()
        }
    }

    fun saveSearchText(searchText: String) {
        if (searchText.isEmpty()) return
        searchTextMutableStateFlow.value = searchText
    }

    sealed interface ViewLoadState {
        object Loading : ViewLoadState
        data class LoadingError(val error: StringDesc?) : ViewLoadState
        data class Loaded(val movies: List<Movie>) : ViewLoadState
        object Input : ViewLoadState
    }

    private sealed interface PrivateLoadState {
        object Loading : PrivateLoadState
        data class LoadingError(val error: StringDesc) : PrivateLoadState
        object Loaded : PrivateLoadState
        object Input : PrivateLoadState
    }
}