package com.android.support.kotlin.core.base

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

abstract class PagedRecyclerAdapter<T>(val view: RecyclerView, DIFF_CALLBACK: DiffUtil.ItemCallback<T>) :
        PagedListAdapter<T, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    init {
        view.layoutManager = LinearLayoutManager(view.context)
        view.adapter = this
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let { (viewHolder as BaseViewHolder<T>).bind(it) }
    }
}
