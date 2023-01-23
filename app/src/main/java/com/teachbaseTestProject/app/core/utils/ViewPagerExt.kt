package com.teachbaseTestProject.app.core.utils

import android.content.res.Resources
import android.util.TypedValue
import android.view.View
import androidx.viewpager2.widget.ViewPager2

fun ViewPager2.setShowSideItems() {

    offscreenPageLimit = 1
    val nextItemVisiblePx = 100.toPx()
    val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
        page.translationY = -nextItemVisiblePx * position
    }
    setPageTransformer(pageTransformer)
}


