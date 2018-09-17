package com.android.support.kotlin.core.base

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

abstract class PagedLoadingAdapter<T>(view: RecyclerView, diffCallback: DiffUtil.ItemCallback<T>) :
        PagedRecyclerAdapter<T>(view, diffCallback) {
    private val TYPE_LOADING = 1
    private val TYPE_ITEM = 2
    var loading = false
        set(value) {
            val itemIndex = super.getItemCount()
            field = value
            if (value) notifyItemInserted(itemIndex)
            else notifyItemRemoved(itemIndex)
        }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_ITEM)
            super.onBindViewHolder(viewHolder, position)
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        return if (p1 == TYPE_LOADING) onCreateLoadingViewHolder(p0)
        else onCreateItemViewHolder(p0, p1)
    }

    override fun getItemViewType(position: Int) =
            if (loading && position == super.getItemCount()) TYPE_LOADING else TYPE_ITEM

    override fun getItemCount() = super.getItemCount() + if (loading) 1 else 0

    abstract fun onCreateItemViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder

    abstract fun onCreateLoadingViewHolder(parent: ViewGroup): RecyclerView.ViewHolder
}