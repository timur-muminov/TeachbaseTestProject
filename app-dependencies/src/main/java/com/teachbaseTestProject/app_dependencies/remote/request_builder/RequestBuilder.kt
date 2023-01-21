package com.teachbaseTestProject.app_dependencies.remote.request_builder

import com.teachbaseTestProject.entities.filter.MovieFilter

interface RequestBuilder {
    fun buildRequestByFilters(movieFilter: MovieFilter, page:Int): String
    fun buildRequestByName(movieName: String, page: Int): String
    fun buildRequestById(movieId: Int): String
}