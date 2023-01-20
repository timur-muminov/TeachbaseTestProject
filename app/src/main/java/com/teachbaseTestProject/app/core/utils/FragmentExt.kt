package com.teachbaseTestProject.app.core.utils

import android.os.Build
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener

fun <T> Fragment.fragmentResultListenerWithParcelable(
    requestKey: String, bundleKey: String, expectedClass: Class<T>, action: (T?) -> Unit
) {
    setFragmentResultListener(requestKey) { _, bundle ->
        val result = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(bundleKey, expectedClass)
        } else {
            @Suppress("DEPRECATION") bundle.getParcelable(bundleKey)
        }
        action(result)
    }
}
