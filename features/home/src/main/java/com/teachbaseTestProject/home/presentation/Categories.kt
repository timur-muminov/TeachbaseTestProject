package com.teachbaseTestProject.home.presentation

import com.teachbaseTestProject.filter.MovieFilter
import com.teachbaseTestProject.filter.MovieType
import com.teachbaseTestProject.filter.SortType
import com.teachbaseTestProject.movie.Category


class Categories {

    val categories = listOf(
        Category(id = 1, name = "Фильмы с высоким рейтингом"),
        Category(id = 2, name = "Фильмы 2022"),
        Category(id = 3, name = "Популярные сериалы"),
        Category(id = 4, name = "Мультфильмы 2022"),
        Category(id = 5, name = "Аниме 2022")
    )

    val categoriesToFilters = mapOf(
        categories[0] to MovieFilter(
            movieType = MovieType.FILMS,
            rate = 9 to 10,
            dateRange = 2000 to 2023,
            sortType = SortType.RATE
        ),
        categories[1] to MovieFilter(
            movieType = MovieType.FILMS,
            rate = 7 to 10,
            dateRange = 2022 to 2023,
            sortType = SortType.RATE
        ),
        categories[2] to MovieFilter(
            movieType = MovieType.SERIES,
            rate = 8 to 10,
            dateRange = 2015 to 2023,
            sortType = SortType.RATE
        ),
        categories[3] to MovieFilter(
            movieType = MovieType.CARTOON,
            rate = 7 to 10,
            dateRange = 2022 to 2023,
            sortType = SortType.RATE
        ),
        categories[4] to MovieFilter(
            movieType = MovieType.ANIME,
            rate = 7 to 10,
            dateRange = 2022 to 2023,
            sortType = SortType.RATE
        )
    )
}