package com.android.support.kotlin.core.livedata

import android.arch.lifecycle.*

open class ExtendLiveData<T> : MediatorLiveData<T>()

fun <T> MutableLiveData<T>.call() {
    this.value = null
}

fun <T> MutableLiveData<T>.refresh() {
    this.value?.let {
        this.value = it
    }
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
    result.addSource<X>(this, object : Observer<X> {
        var mSource: LiveData<Y>? = null
        override fun onChanged(t: X?) {
            val newLiveData = function.invoke(t)
            if (mSource === newLiveData) return
            if (mSource != null) result.removeSource<Y>(mSource!!)
            mSource = newLiveData
            if (mSource != null) result.addSource<Y>(mSource!!) { result.value = it }
        }
    })
    return result
}

fun <T> LiveData<T>.forwardTo(liveData: ExtendLiveData<T>) {
    liveData.addSource(this) {
        liveData.postValue(it)
    }
}

fun <V, T> V?.nonNull(function: (V) -> LiveData<T>): LiveData<T> =
        if (this != null) function.invoke(this) else ExtendLiveData()

fun <T> LiveData<T>.filter(function: (T?) -> Boolean): LiveData<T> {
    val liveData = MediatorLiveData<T>()
    liveData.addSource(this) {
        if (function.invoke(value)) liveData.value = it
    }
    return liveData
}