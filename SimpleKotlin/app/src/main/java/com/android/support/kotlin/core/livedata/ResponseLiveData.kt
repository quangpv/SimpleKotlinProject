package com.android.support.kotlin.core.livedata

import android.arch.lifecycle.MutableLiveData

class ResponseLiveData<T> : ExtendLiveData<T>() {
    private var mLoading: MutableLiveData<Boolean>? = null

    val loading: MutableLiveData<Boolean>
        get() {
            if (mLoading == null) mLoading = MutableLiveData()
            return mLoading ?: throw AssertionError("Can't to init loading")
        }

    private var mError: MutableLiveData<Exception>? = null
    val error: MutableLiveData<Exception>
        get() {
            if (mError == null) mError = MutableLiveData()
            return mError ?: throw AssertionError("Can't to init error")
        }

    fun loading(loading: Boolean) {
        this.loading.postValue(loading)
    }

    fun error(error: Exception) {
        this.error.postValue(error)
    }

    fun notifyLoadingTo(loading: SingleLiveEvent<Boolean>): ResponseLiveData<T> {
        loading.addSource(this.loading) { _ ->
            val isLoading = loading.liveDataSources
                    .asSequence()
                    .map { if (it.value == null) false else it.value as Boolean }
                    .reduce { sum, next -> sum || next }
            if (loading.value != isLoading) loading.postValue(isLoading)
        }
        return this
    }

    fun notifyErrorTo(error: SingleLiveEvent<Exception>): ResponseLiveData<T> {
        error.addSource(this.error) {
            error.postValue(it)
        }
        return this
    }
}