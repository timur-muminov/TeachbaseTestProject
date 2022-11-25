package com.ecommerceconcept.app.utils

import java.text.NumberFormat
import java.util.*

fun String.toPriceFormat(extraText: String = "") = "$" + NumberFormat.getNumberInstance(Locale.US).format(this.toInt()) + extraText
fun String.toMemoryFormat() = "$this GB"