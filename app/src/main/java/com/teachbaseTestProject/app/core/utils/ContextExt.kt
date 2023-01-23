package com.teachbaseTestProject.app.core.utils

import android.content.Context
import com.teachbasetestproject.app.R

fun Context.getColorFromRate(rate: Float?): Int {
    if (rate == null) return 0
    val color = if (rate < 7) R.color.gray_5 else R.color.green_1
    return getColor(color)
}
