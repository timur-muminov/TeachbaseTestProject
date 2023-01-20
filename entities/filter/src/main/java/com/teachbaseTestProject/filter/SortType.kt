package com.teachbaseTestProject.filter

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class SortType : Parcelable {
    RATE,
    YEAR,
    POPULARITY
}
