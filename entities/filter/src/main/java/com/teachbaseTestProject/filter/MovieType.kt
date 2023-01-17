package com.teachbaseTestProject.filter

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class MovieType(val id: Int) : Parcelable {
    ALL(1),
    FILMS(2),
    SERIES(3),
    CARTOON(4),
    ANIME(5)
}
