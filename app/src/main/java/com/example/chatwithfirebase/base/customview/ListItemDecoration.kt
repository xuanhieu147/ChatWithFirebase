package com.example.chatwithfirebase.base.customview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration



class ListItemDecoration(private val spanCount: Int,
                         private val spacing: Int,
                         private val includeEdge: Boolean
                         ) : ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view) // item position
        if (includeEdge) {
            if (position < spanCount) { // top edge
                outRect.top = spacing
            } else {
                outRect.top = spacing / 2
            }
            outRect.bottom = spacing / 2
        } else {
            if (position >= spanCount) {
                outRect.top = spacing // item top
            }
        }
    }
}