package com.example.chatwithfirebase.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/** Copy by Duc Minh */
abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun onBind(position: Int)
}