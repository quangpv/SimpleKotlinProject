package com.android.support.kotlin.core.network

import android.util.Log
import com.example.kantek.simplekotlin.BuildConfig
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class RequestBound<ResultType, RequestType> : BaseRequestBound<ResultType>() {
    override fun execute() {
        val localData = loadFromLocal()

        if (isFetchData(localData)) {
            if (isMock()) onDataLocal(createMockData())
            else fetchFromRemote()
        } else {
            onDataLocal(localData)
        }
    }

    protected open fun createMockData(): ResultType? = null

    protected open fun isMock(): Boolean = BuildConfig.MOCK_DATA

    protected open fun isFetchData(localData: ResultType?): Boolean = localData == null

    private fun fetchFromRemote() {
        loading(true)
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

                val result = convertToResult(body.result)
                saveCallResult(result)
                onSuccess(result)
            }

            override fun onFailure(call: Call<ApiResponse<RequestType>>, t: Throwable) {
                onError(Exception(t))
            }
        })
    }

    private fun onError(exception: Exception) {
        loading(false)
        error(exception)
        Log.e("REQUEST/ERROR", "${exception.message}")
        onCallFail(exception)
    }

    private fun onSuccess(result: ResultType?) {
        loading(false)
        mLiveData.value = result
        Log.e("REQUEST/SUCCESS", Gson().toJson(result))
    }

    private fun onDataLocal(localData: ResultType?) {
        mLiveData.value = localData
    }

    protected open fun onCallFail(exception: Exception) {
    }

    protected open fun saveCallResult(result: ResultType?) {
    }

    protected abstract fun convertToResult(result: RequestType?): ResultType?

    abstract fun createCall(): Call<ApiResponse<RequestType>>?

    protected open fun loadFromLocal(): ResultType? = null

}