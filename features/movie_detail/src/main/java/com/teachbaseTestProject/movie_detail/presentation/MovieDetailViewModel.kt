package com.teachbaseTestProject.movie_detail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teachbaseTestProject.entities.movie.Category
import com.teachbaseTestProject.entities.movie.Movie
import com.teachbaseTestProject.entities.movie.MovieDetail
import dev.icerock.moko.errors.handler.ExceptionHandler
import dev.icerock.moko.errors.mappers.mapThrowable
import dev.icerock.moko.resources.desc.StringDesc
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val exceptionHandler: ExceptionHandler,
) : ViewModel() {

    private val privateLoadState = MutableStateFlow<PrivateLoadState>(PrivateLoadState.NotExecuted)

    init {
        load()
    }

    fun load() {
        if (privateLoadState.value == PrivateLoadState.Loading) return
        viewModelScope.launch {
            exceptionHandler.handle {
                privateLoadState.value = PrivateLoadState.Loading
                //byFiltersSearchable.refresh()
                privateLoadState.value = PrivateLoadState.Loaded
            }.catch<Exception> { error ->
                privateLoadState.value = PrivateLoadState.LoadingError(error.mapThrowable())
                true
            }.execute()
        }
    }

    sealed interface ViewLoadState {
        object Loading : ViewLoadState
        data class LoadingError(val error: StringDesc?) : ViewLoadState
        data class Loaded(val movieDetail: MovieDetail) : ViewLoadState
    }

    private sealed interface PrivateLoadState {
        object Loading : PrivateLoadState
        data class LoadingError(val error: StringDesc) : PrivateLoadState
        object Loaded : PrivateLoadState
        object NotExecuted : PrivateLoadState
    }
}