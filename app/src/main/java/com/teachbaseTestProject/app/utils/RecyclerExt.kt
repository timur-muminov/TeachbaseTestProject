package com.teachbaseTestProject.app.utils

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE

fun RecyclerView.setPagination(action: () -> Unit) =
    this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            if (!recyclerView.canScrollVertically(1) && newState == SCROLL_STATE_IDLE) {
                action()
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {}
    })