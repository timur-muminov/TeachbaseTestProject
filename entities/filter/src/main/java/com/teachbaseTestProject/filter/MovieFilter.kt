package com.teachbaseTestProject.filter

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieFilter(
    val movieType: MovieType?,
    val rate: Pair<Int, Int>?,
    val dateRange: Pair<Int, Int>?,
    val sortType: SortType?
) : Parcelable