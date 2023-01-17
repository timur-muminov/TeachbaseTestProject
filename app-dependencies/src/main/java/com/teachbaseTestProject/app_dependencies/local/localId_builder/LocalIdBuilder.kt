package com.teachbaseTestProject.app_dependencies.local.localId_builder

import com.teachbaseTestProject.filter.MovieFilter

interface LocalIdBuilder {
    fun buildLocalIdByFilters(movieFilter: MovieFilter): String
    fun buildLocalIdByName(movieName: String): String
}