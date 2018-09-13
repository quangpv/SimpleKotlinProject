package com.android.support.kotlin.core.base

import android.arch.lifecycle.ViewModel
import com.android.support.kotlin.core.livedata.SingleLiveEvent

open class BaseViewModel : ViewModel() {
    var error = SingleLiveEvent<Exception>()
    var loading = SingleLiveEvent<Boolean>()
}