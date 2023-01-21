package com.teachbaseTestProject.app_dependencies.local.localId_builder

import com.teachbaseTestProject.entities.filter.MovieFilter

class LocalIdBuilderImpl : LocalIdBuilder {

    private val byFilterKey = "filter"
    private val byNameKey = "name"

    override fun buildLocalIdByFilters(movieFilter: MovieFilter): String =
        byFilterKey + movieFilter.movieType.name + movieFilter.dateRange + movieFilter.sortType + movieFilter.rate

    override fun buildLocalIdByName(movieName: String): String = byNameKey + movieName
}