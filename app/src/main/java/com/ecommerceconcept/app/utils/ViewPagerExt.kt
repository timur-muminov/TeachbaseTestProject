package com.ecommerceconcept.app.utils

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs
import kotlin.math.max


fun getPageTransformer(minScale: Float) = ViewPager2.PageTransformer { view, position ->
    view.apply {
        val pageWidth = width
        val pageHeight = height

        val scaleFactor = max(minScale, 1 - abs(position))
        val vertMargin = pageHeight * (1 - scaleFactor) / 2
        val horizMargin = pageWidth * (1 - scaleFactor) / 2
        translationX = if (position < 0) {
            horizMargin - vertMargin / 2
        } else {
            horizMargin + vertMargin / 2
        }

        scaleX = scaleFactor
        scaleY = scaleFactor
    }
}

fun ViewPager2.setShowSideItems() {

    offscreenPageLimit = 1

    val nextItemVisiblePx = 55.toPx()
    val currentItemHorizontalMarginPx = 74.toPx()
    val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
    val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
        page.translationX = -pageTranslationX * position

        val value = 1 - (0.25f * abs(position))
        page.scaleY = value
        page.scaleX = value

    }
    setPageTransformer(pageTransformer)
}

