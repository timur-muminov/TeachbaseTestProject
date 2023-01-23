package com.teachbaseTestProject.app.core.decorator

import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.teachbaseTestProject.app.core.decorator.base.Decorator
import com.teachbaseTestProject.app.core.utils.toPx

class LinearDividerDrawer(private val decorConfig: DecorConfig) : Decorator.ViewHolderDecor {

    private val dividerPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val alpha = dividerPaint.alpha

    init {
        dividerPaint.color = decorConfig.color
        dividerPaint.strokeWidth = decorConfig.height.toFloat()
    }

    override fun draw(
        canvas: Canvas,
        view: View,
        recyclerView: RecyclerView,
        state: RecyclerView.State
    ) {
        val viewHolder = recyclerView.getChildViewHolder(view)
        val nextViewHolder = recyclerView.findViewHolderForAdapterPosition(viewHolder.adapterPosition + 1)

        val startX = recyclerView.paddingLeft + decorConfig.paddingStart.toPx()
        val startY = view.bottom + view.translationY
        val stopX = recyclerView.width - recyclerView.paddingRight - decorConfig.paddingEnd
        val stopY = startY

        dividerPaint.alpha = (view.alpha * alpha).toInt()

        val areSameHolders =
            viewHolder.itemViewType == (nextViewHolder?.itemViewType ?: false)

        if (areSameHolders) {
            canvas.drawLine(startX.toFloat(), startY, stopX.toFloat(), stopY, dividerPaint)
        }
    }
}