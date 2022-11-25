package com.ecommerceconcept.app.utils

import android.widget.EditText

fun EditText.setTextIfNotEquals(newText: String) {
    if (text.toString() != newText) setText(newText)
}