package com.teachbaseTestProject.app.utils

import androidx.fragment.app.Fragment

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}