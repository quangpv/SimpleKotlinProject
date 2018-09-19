package com.example.kantek.simplekotlin.helpers

import android.util.Log
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.Worker
import java.lang.Exception
import java.util.concurrent.TimeUnit

class DownloadWorker : Worker() {

    override fun doWork(): Result {
        Log.e(TAG, "Downloading")
        try {
            Thread.sleep(4000)
            Log.e(TAG, "Download finished")
            outputData = Data.Builder()
                    .putInt("data", inputData.getInt("data", 0) + 1)
                    .build()
        } catch (e: Exception) {
            return Result.FAILURE
        }
        return Result.SUCCESS
    }

    companion object {
        fun oneTimeWork(it: Int) = OneTimeWorkRequestBuilder<DownloadWorker>()
                .setInitialDelay(1, TimeUnit.SECONDS)
                .setInputData(Data.Builder()
                        .putInt("data", it)
                        .build())
                .build()

        const val TAG = "DownloadWorker"

    }
}