package com.sparta.applemarket.util

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.sparta.applemarket.util.FloatUtil.fromDpToPx

class RecyclerViewDecoration : RecyclerView.ItemDecoration() {
    private val margin = 12f.fromDpToPx()
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        val paint = Paint().apply {
            color = Color.GRAY
        }
        val left = parent.paddingLeft.toFloat()
        val right = parent.width - parent.paddingRight.toFloat()
        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            // 그릴 때 탑 마진 포함
            val top =
                child.bottom.toFloat() + params.bottomMargin + margin
            val bottom = top + 1.5f.fromDpToPx()
            c.drawRect(left + margin, top, right - margin, bottom, paint)
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        // 바텀 마진
        outRect.bottom = 12
    }
}