package com.teachbaseTestProject.entities.filter

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class SortType : Parcelable {
    RATE,
    YEAR,
    POPULARITY
}
