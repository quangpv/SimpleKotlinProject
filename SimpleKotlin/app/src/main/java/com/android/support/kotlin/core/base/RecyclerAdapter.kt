package com.android.support.kotlin.core.base

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

abstract class RecyclerAdapter<T>(val view: RecyclerView) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mItems: MutableList<T>? = null
    var items: MutableList<T>?
        get() = mItems
        set(value) {
            mItems = value
            notifyDataSetChanged()
        }

    init {
        view.layoutManager = LinearLayoutManager(view.context)
        view.adapter = this
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        (p0 as? BaseViewHolder<T>)?.bind(mItems!![p1])
    }

    override fun getItemCount() = if (mItems == null) 0 else mItems!!.size
}