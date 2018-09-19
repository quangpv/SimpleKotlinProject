package com.android.support.kotlin.core.livedata

class LoadingEvent : SingleLiveEvent<Boolean>() {
    private var numOfLoading = 0

    override fun postValue(value: Boolean?) {
        synchronized(this) {
            if (value!!) {
                numOfLoading++
                if (value != this.value) super.postValue(true)
            } else {
                numOfLoading--
                if (numOfLoading < 0) numOfLoading = 0
                if (numOfLoading == 0) super.postValue(false)

            }
        }
    }
}