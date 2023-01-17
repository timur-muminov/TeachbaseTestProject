package com.teachbaseTestProject.app.fragments.main.filter.utils

import com.google.android.material.tabs.TabLayout

fun TabLayout.onTabSelectedListener(onTabSelected: (TabLayout.Tab) -> Unit) =
    addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab) = onTabSelected(tab)
        override fun onTabUnselected(tab: TabLayout.Tab) {}
        override fun onTabReselected(tab: TabLayout.Tab) {}
    })