package com.teachbaseTestProject.filter

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class MovieType : Parcelable {
    ALL,
    FILMS,
    SERIES,
    CARTOON,
    ANIME
}
