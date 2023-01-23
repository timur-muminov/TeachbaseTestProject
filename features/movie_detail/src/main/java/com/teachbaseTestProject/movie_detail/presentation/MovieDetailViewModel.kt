package com.teachbaseTestProject.movie_detail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teachbaseTestProject.entities.movie.MovieDetail
import com.teachbaseTestProject.movie_detail.model.MovieDetailRepository
import dev.icerock.moko.errors.handler.ExceptionHandler
import dev.icerock.moko.errors.mappers.mapThrowable
import dev.icerock.moko.resources.desc.StringDesc
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val movieId: Int,
    private val movieDetailRepository: MovieDetailRepository,
    private val exceptionHandler: ExceptionHandler,
) : ViewModel() {

    private val privateLoadState = MutableStateFlow<PrivateLoadState>(PrivateLoadState.NotExecuted)

    init {
        loadData()
    }

    val state = combine(
        privateLoadState,
        movieDetailRepository.movieDetailFlow(movieId)
    ) { privateState, movieDetail ->
        when (privateState) {
            is PrivateLoadState.Loaded -> ViewLoadState.Loaded(movieDetail)
            is PrivateLoadState.Loading -> ViewLoadState.Loading
            is PrivateLoadState.LoadingError -> ViewLoadState.LoadingError(privateState.error)
            is PrivateLoadState.NotExecuted -> ViewLoadState.Loading
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), ViewLoadState.Loading)

    fun loadData() {
        if (privateLoadState.value == PrivateLoadState.Loading) return
        viewModelScope.launch {
            exceptionHandler.handle {
                privateLoadState.value = PrivateLoadState.Loading
                movieDetailRepository.refreshMovieDetailById(movieId)
                privateLoadState.value = PrivateLoadState.Loaded
            }.catch<Exception> { error ->
                error.printStackTrace()
                privateLoadState.value = PrivateLoadState.LoadingError(error.mapThrowable())
                true
            }.execute()
        }
    }

    sealed interface ViewLoadState {
        object Loading : ViewLoadState
        data class LoadingError(val error: StringDesc?) : ViewLoadState
        data class Loaded(val movieDetail: MovieDetail?) : ViewLoadState
    }

    private sealed interface PrivateLoadState {
        object Loading : PrivateLoadState
        data class LoadingError(val error: StringDesc) : PrivateLoadState
        object Loaded : PrivateLoadState
        object NotExecuted : PrivateLoadState
    }
}