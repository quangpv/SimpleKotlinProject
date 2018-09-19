package com.android.support.kotlin.core.network

import android.arch.paging.DataSource
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList

abstract class PageRequestBound<PageType>(private val pageSize: Int) : BaseRequestBound<PagedList<PageType>>() {

    override fun execute() {
        mLiveData.addSource(LivePagedListBuilder(this.createDataSource(), PagedList.Config.Builder()
                .setPageSize(pageSize)
                .setInitialLoadSizeHint(pageSize)
                .setEnablePlaceholders(true)
                .build())
                .build())
        {
            mLiveData.value = it
        }
    }

    abstract fun createDataSource(): DataSource.Factory<Int, PageType>

}