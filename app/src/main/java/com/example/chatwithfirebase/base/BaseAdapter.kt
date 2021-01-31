package com.example.chatwithfirebase.base

import androidx.recyclerview.widget.RecyclerView

/**
 * Copy by Duc Minh
 */

abstract class BaseAdapter<T, V> :
    RecyclerView.Adapter<BaseViewHolder>() {
    abstract fun addItems(repoList: List<T>)
    abstract fun clearItems()
}
