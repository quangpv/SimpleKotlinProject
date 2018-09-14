package com.android.support.kotlin.core

import android.os.Handler
import android.os.Looper

import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class AppExecutors {
    private val mDiskIO = Executors.newSingleThreadExecutor()
    private val mNetworkIO = Executors.newFixedThreadPool(3)
    private val mMainThread = MainThreadExecutor()

    fun diskIO(): ExecutorService {
        return mDiskIO
    }

    fun networkIO(): ExecutorService {
        return mNetworkIO
    }

    fun mainThread(): MainThreadExecutor {
        return mMainThread
    }

    inner class MainThreadExecutor : Executor {
        private val mHandler = Handler(Looper.getMainLooper())

        override fun execute(runnable: Runnable) {
            mHandler.post(runnable)
        }
    }
}