package com.android.support.kotlin.core.network

import android.arch.paging.DataSource
import android.arch.paging.PageKeyedDataSource
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class PageByIndexRequestBound<ResultType, RequestType>(pageSize: Int)
    : PageRequestBound<ResultType>(pageSize) {

    override fun createDataSource() = object : DataSource.Factory<Int, RequestType>() {
        override fun create() = object : PageKeyedDataSource<Int, RequestType>() {
            override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, RequestType>) {
                fetchFromRemote(1, params.requestedLoadSize) { result, nextPage ->
                    callback.onResult(result, null, nextPage)
                }
            }

            override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, RequestType>) {
            }

            override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, RequestType>) {
                fetchFromRemote(params.key, params.requestedLoadSize) { result, next ->
                    callback.onResult(result, next)
                }
            }
        }
    }.map(this::convertToResult)

    private fun fetchFromRemote(page: Int?, pageSize: Int, callback: (MutableList<RequestType>, Int) -> Unit) {
        val call = createCall(page, pageSize) ?: throw RuntimeException("Call can not be null")
        mLiveData.loading(true)
        call.enqueue(object : Callback<ApiPageResponse<RequestType>> {
            override fun onResponse(call: Call<ApiPageResponse<RequestType>>, response: Response<ApiPageResponse<RequestType>>) {
                if (!response.isSuccessful) {
                    onError(Exception(response.message()))
                    return
                }
                val apiPageBody = response.body()
                if (apiPageBody == null) {
                    onError(Exception("Null"))
                    return
                }
                onSuccess(apiPageBody, callback)
            }

            override fun onFailure(call: Call<ApiPageResponse<RequestType>>, t: Throwable) {
                onError(Exception(t))
            }
        })
    }

    abstract fun createCall(page: Int?, pageSize: Int): Call<ApiPageResponse<RequestType>>?

    private fun onSuccess(result: ApiPageResponse<RequestType>,
                          callback: (MutableList<RequestType>, Int) -> Unit) {
        result.result?.let { callback.invoke(it, result.nextPage) }
        mLiveData.loading(false)
        Log.e("RESPONSE_SUCCESS", "${result.result?.size}")
    }

    private fun onError(e: Exception) {
        mLiveData.error(e)
        mLiveData.loading(false)
        Log.e("RESPONSE_ERROR", e.message)
    }

    abstract fun convertToResult(it: RequestType): ResultType

}