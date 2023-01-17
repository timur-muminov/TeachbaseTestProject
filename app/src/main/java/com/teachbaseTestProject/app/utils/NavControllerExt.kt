package com.teachbaseTestProject.app.utils

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavDirections

fun NavController.safeActionNavigate(
    actionId: NavDirections
) = try {
    navigate(actionId)
} catch (e: Throwable) {
    Log.e("error", e.stackTraceToString())
}