package com.teachbaseTestProject.app.fragments.main.filter.utils

import android.widget.SeekBar

fun SeekBar.onSeekBarChanged(onProgressChanged: (Int) -> Unit) =
    setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) =
            onProgressChanged(progress)

        override fun onStartTrackingTouch(seekBar: SeekBar?) {}
        override fun onStopTrackingTouch(seekBar: SeekBar?) {}
    })