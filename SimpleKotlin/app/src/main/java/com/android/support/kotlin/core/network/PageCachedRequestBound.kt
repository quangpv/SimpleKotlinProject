package com.android.support.kotlin.core.network

import android.util.Log
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class PageCachedRequestBound<PageType, ResultType, RequestType>(pageSize: Int) : PageRequestBound<PageType>(pageSize) {

    init {
        fetchFromRemote()
    }

    private fun fetchFromRemote() {
        mLiveData.loading(true)
        val call = createCall() ?: throw RuntimeException("Call not be null")
        call.enqueue(object : Callback<ApiResponse<RequestType>> {
            override fun onResponse(call: Call<ApiResponse<RequestType>>, response: Response<ApiResponse<RequestType>>) {
                if (!response.isSuccessful) {
                    onError(Exception(response.message()))
                    return
                }
                val body = response.body()
                if (body == null) {
                    onError(Exception("Unknown"))
                    return
                }

                if (!body.isSuccess()) {
                    onError(Exception(body.message))
                    return
                }
                onSuccess(body.result)
            }

            override fun onFailure(call: Call<ApiResponse<RequestType>>, t: Throwable) {
                onError(Exception(t))
            }
        })
    }

    private fun onError(exception: Exception) {
        mLiveData.loading(false)
        mLiveData.error(exception)
        onCallFail(exception)
        Log.e("REQUEST/PAGE/ERROR", "${exception.message}")
    }

    private fun onSuccess(result: RequestType?) {
        mAppExecutors.diskIO().execute {
            saveCallResult(convertToResult(result))
            mLiveData.loading(false)
            Log.e("REQUEST/PAGE/SUCCESS", Gson().toJson(result))
        }
    }

    protected open fun onCallFail(exception: Exception) {
    }

    protected open fun saveCallResult(result: ResultType?) {
    }

    protected abstract fun convertToResult(result: RequestType?): ResultType?

    abstract fun createCall(): Call<ApiResponse<RequestType>>?

}