package com.teachbaseTestProject.filter.presentation

import com.teachbaseTestProject.entities.filter.MovieFilter
import com.teachbaseTestProject.entities.filter.MovieType
import com.teachbaseTestProject.entities.filter.SortType
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class FilterViewModel : ViewModel() {

    private val defaultMovieFilter = MovieFilter(
        movieType = MovieType.ALL,
        rate = 7 to 10,
        dateRange = 2020 to 2023,
        sortType = SortType.RATE
    )

    private val movieFilterMutableStateFlow = MutableStateFlow(defaultMovieFilter)
    val movieFilterStateFlow: Flow<MovieFilter> = movieFilterMutableStateFlow

    fun setMovieType(movieType: MovieType) {
        movieFilterMutableStateFlow.update { it.copy(movieType = movieType) }
    }

    fun setRate(rate: Pair<Int, Int>) {
        movieFilterMutableStateFlow.update { it.copy(rate = rate) }
    }

    fun setDateRange(dateRange: Pair<Int, Int>) {
        movieFilterMutableStateFlow.update { it.copy(dateRange = dateRange) }
    }

    fun setSortType(sortType: SortType) {
        movieFilterMutableStateFlow.update { it.copy(sortType = sortType) }
    }

    fun clearFilters() {
        movieFilterMutableStateFlow.value = defaultMovieFilter
    }
}