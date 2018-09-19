package com.example.kantek.simplekotlin.helpers

import android.util.Log
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.Worker
import java.lang.Exception
import java.util.concurrent.TimeUnit

class UnZipWorker : Worker() {
    override fun doWork(): Result {
        Log.e(TAG, "Start Unzip")
        try {
            Thread.sleep(10000)
            Log.e(TAG, "Unzip Finish")
        } catch (ex: Exception) {
            return Result.FAILURE
        }
        return Result.SUCCESS
    }

    companion object {
        fun oneTimeWork() = OneTimeWorkRequestBuilder<UnZipWorker>()
                .setInitialDelay(1, TimeUnit.SECONDS)
                .build()

        const val TAG = "UnZipWorker"
    }
}