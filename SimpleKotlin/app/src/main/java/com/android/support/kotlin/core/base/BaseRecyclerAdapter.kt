package com.android.support.kotlin.core.base

import android.support.annotation.LayoutRes
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseRecyclerAdapter<T>(val view: RecyclerView) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
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
        (p0 as? ViewHolder<T>)?.bind(mItems!![p1])
    }

    override fun getItemCount() = if (mItems == null) 0 else mItems!!.size

    open class ViewHolder<T>(parent: ViewGroup, @LayoutRes id: Int) : RecyclerView.ViewHolder(inflate(parent, id)) {

        protected var mItem: T? = null

        open fun bind(item: T) {
            mItem = item
        }

        companion object {
            fun inflate(parent: ViewGroup, id: Int): View =
                    LayoutInflater.from(parent.context)
                            .inflate(id, parent, false)
        }

    }
}