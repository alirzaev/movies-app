package io.github.alirzaev.movies.features.moviedetails

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class HorizontalGapItemDecoration(private val margin: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val pos = parent.getChildLayoutPosition(view)
        with(outRect) {
            if (pos > 0) {
                left = margin
            }
        }
    }
}