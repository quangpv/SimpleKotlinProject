package com.android.support.kotlin.core.base

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

open class BaseViewHolder<T>(parent: ViewGroup, @LayoutRes id: Int) : RecyclerView.ViewHolder(inflate(parent, id)) {

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