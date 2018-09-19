package com.android.support.kotlin.core.network

import android.arch.lifecycle.LiveData
import com.android.support.kotlin.core.AppExecutors
import com.android.support.kotlin.core.livedata.ExtendLiveData
import com.android.support.kotlin.core.livedata.LoadingEvent
import com.android.support.kotlin.core.livedata.SingleLiveEvent

open abstract class BaseRequestBound<ResultType> {
    private var mLoading: LoadingEvent? = null
    private var mError: SingleLiveEvent<Exception>? = null
    protected var mLiveData = ExtendLiveData<ResultType>()
    protected var mAppExecutors = AppExecutors()

    fun notifyLoadingTo(loading: LoadingEvent): BaseRequestBound<ResultType> {
        mLoading = loading
        return this
    }

    fun notifyErrorTo(error: SingleLiveEvent<Exception>): BaseRequestBound<ResultType> {
        mError = error
        return this
    }

    protected fun loading(loading: Boolean) {
        mLoading?.postValue(loading)
    }

    protected fun error(error: Exception) {
        mError?.value = error
    }

    fun asLiveData(): LiveData<ResultType> {
        execute()
        return mLiveData
    }

    protected abstract fun execute()

}