package com.android.support.kotlin.core.livedata

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer

open class ExtendLiveData<T> : MediatorLiveData<T>() {
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

    protected val mLiveDataSources: MutableList<LiveData<*>> = ArrayList()

    fun loading(loading: Boolean) {
        this.loading.value = loading
    }

    fun error(error: Exception) {
        this.error.value = error
    }

    fun notifyLoadingTo(loading: SingleLiveEvent<Boolean>): ExtendLiveData<T> {
        loading.addSource(this.loading) { _ ->
            val isLoading = loading.mLiveDataSources
                    .map { if (it.value == null) false else it.value as Boolean }
                    .reduce { sum, next -> sum || next }
            if (loading.value != isLoading) loading.value = isLoading
        }
        return this
    }

    fun notifyErrorTo(error: ExtendLiveData<Exception>): ExtendLiveData<T> {
        error.addSource(this.error) {
            error.value = it
        }
        return this
    }

    override fun <S : Any?> addSource(source: LiveData<S>, onChanged: Observer<S>) {
        super.addSource(source, onChanged)
        mLiveDataSources.add(source)
    }

    override fun <S : Any?> removeSource(toRemote: LiveData<S>) {
        super.removeSource(toRemote)
        mLiveDataSources.remove(toRemote)
    }
}

fun <T> MutableLiveData<T>.call() {
    this.value = null
}

fun <T> LiveData<T>.observe(lifecycleOwner: LifecycleOwner, function: (T?) -> Unit) {
    this.observe(lifecycleOwner, Observer { function.invoke(it) })
}

fun <X, Y> LiveData<X>.map(function: (X?) -> Y): LiveData<Y> {
    val result = MediatorLiveData<Y>()
    result.addSource(this) { result.value = function.invoke(it) }
    return result
}

fun <X, Y> LiveData<X>.switchTo(function: (X?) -> LiveData<Y>): LiveData<Y> {
    val result = MediatorLiveData<Y>()
    var mSource: LiveData<Y>? = null
    result.addSource<X>(this) { it ->
        val newLiveData = function.invoke(it)
        if (mSource === newLiveData) return@addSource
        if (mSource != null) result.removeSource<Y>(mSource!!)
        mSource = newLiveData
        if (mSource != null) result.addSource<Y>(mSource!!) { result.value = it }
    }
    return result
}

fun <T> LiveData<T>.filter(function: (T?) -> Boolean): LiveData<T> {
    val liveData = MediatorLiveData<T>()
    liveData.addSource(this) {
        if (function.invoke(value)) liveData.value = it
    }
    return liveData
}