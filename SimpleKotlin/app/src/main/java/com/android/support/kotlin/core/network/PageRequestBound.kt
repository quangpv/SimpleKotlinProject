package com.android.support.kotlin.core.network

import android.arch.paging.DataSource
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.android.support.kotlin.core.AppExecutors
import com.android.support.kotlin.core.livedata.ResponseLiveData

abstract class PageRequestBound<PageType>(pageSize: Int) {
    protected var mLiveData = ResponseLiveData<PagedList<PageType>>()
    protected var mAppExecutors = AppExecutors()

    init {
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

    fun asLiveData(): ResponseLiveData<PagedList<PageType>> = mLiveData

}