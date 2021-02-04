package com.example.chatwithfirebase.base.customview

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Copy by Duc Minh
 */

abstract class EndlessRecyclerOnScrollListener(
    private val linearLayoutManager: LinearLayoutManager
    ) : RecyclerView.OnScrollListener() {

    companion object {
        val TAG = EndlessRecyclerOnScrollListener::class.java.simpleName
    }

    private var loading = false // True if we are still waiting for the last set of data to load.
    private val visibleThreshold = 1 // The minimum amount of items to have below your current scroll position before loading more.
    private var firstVisibleItem = 0
    var visibleItemCount: Int = 0
    var totalItemCount: Int = 0

    private var currentPage = 1

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (dy < 0) {
            return
        }
        // check for scroll down only
        visibleItemCount = recyclerView.childCount
        totalItemCount = linearLayoutManager.itemCount
        firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition()
        // to make sure only one onLoadMore is triggered
        if (!loading && totalItemCount - visibleItemCount - firstVisibleItem <= visibleThreshold) { // End has been reached, Do something
            currentPage++
            onLoadMore(currentPage)
            loading = true
        }
    }

    fun setLoading(loading: Boolean) {
        this.loading = loading
    }

    fun setCurrentPage(page: Int) {
        currentPage = page
    }

    abstract fun onLoadMore(current_page: Int)

}