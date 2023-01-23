package com.teachbaseTestProject.app.core.utils

fun String?.formatToRateValue() =
    if (!isNullOrEmpty()) {
        String.format("%.1f", this.toFloat()).replace(",", ".")
    } else ""




