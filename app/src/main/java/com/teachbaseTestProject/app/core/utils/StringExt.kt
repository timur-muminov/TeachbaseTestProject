package com.teachbaseTestProject.app.core.utils

fun String.formatToRateValue() = String.format("%.1f", this.toFloat()).replace(",", ".")