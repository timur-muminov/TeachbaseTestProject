package com.teachbaseTestProject.app_dependencies.remote.request_builder

import com.teachbaseTestProject.app_dependencies.token_storage.TokenStorage
import com.teachbaseTestProject.entities.filter.MovieFilter
import com.teachbaseTestProject.entities.filter.MovieType
import com.teachbaseTestProject.entities.filter.SortType

class RequestBuilderImpl(
    tokenStorage: TokenStorage
) : RequestBuilder {

    private val movie = "movie?"
    private val field = "&field="
    private val search = "&search="
    private val sortField = "&sortField="
    private val sortType = "&sortType="

    private val pageField = "&page="

    private val sortByDescendingNumber = "-1"

    private val token = "&token=${tokenStorage.getToken()}"

    override fun buildRequestByFilters(movieFilter: MovieFilter, page: Int): String {
        val request: StringBuilder = StringBuilder()
        request.append(movie)

        if (movieFilter.movieType != MovieType.ALL) {
            val movieType = buildFilter(Filters.TYPE, movieFilter.movieType.getTypeNumber())
            request.append(movieType)
        }

        val rate = buildFilter(Filters.RATING, "${movieFilter.rate.first}-${movieFilter.rate.second}")
        request.append(rate)

        val dateRange = buildFilter(Filters.YEAR, "${movieFilter.dateRange.first}-${movieFilter.dateRange.second}")
        request.append(dateRange)

        val sortType = sortField + movieFilter.sortType.getTypeField() + sortType + sortByDescendingNumber
        request.append(sortType)

        request.append(pageField + page)
        request.append(token)
        return request.toString()
    }

    override fun buildRequestByName(movieName: String, page: Int): String {
        return buildFilter(Filters.NAME, movieName) + pageField + page + token
    }

    override fun buildRequestById(movieId: Int): String {
        return buildFilter(Filters.ID, movieId.toString()) + token
    }

    private fun buildFilter(filters: Filters, value: String): String {
        val type = filters.getTypeField()
        return field + type + search + value
    }

    private enum class Filters {
        ID,
        YEAR,
        RATING,
        VOTES,
        NAME,
        TYPE
    }

    private fun Filters.getTypeField() =
        when (this) {
            Filters.ID -> "id"
            Filters.YEAR -> "year"
            Filters.RATING -> "rating.kp"
            Filters.VOTES -> "votes.kp"
            Filters.NAME -> "name"
            Filters.TYPE -> "typeNumber"
        }

    private fun MovieType.getTypeNumber() =
        when (this) {
            MovieType.ALL -> ""
            MovieType.FILMS -> "1"
            MovieType.SERIES -> "2"
            MovieType.CARTOON -> "3"
            MovieType.ANIME -> "4"
        }

    private fun SortType.getTypeField() =
        when (this) {
            SortType.RATE -> "rating.kp"
            SortType.YEAR -> "year"
            SortType.POPULARITY -> "popularity"
        }
}