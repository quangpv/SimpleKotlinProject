package com.android.support.kotlin.core.network

import android.arch.paging.DataSource
import android.arch.paging.PageKeyedDataSource
import android.util.Log
import com.example.kantek.simplekotlin.BuildConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class PageByIndexRequestBound<ResultType, RequestType>(pageSize: Int)
    : PageRequestBound<ResultType>(pageSize) {
    private val MAX_MOCK_PAGE = 5

    override fun createDataSource() = object : DataSource.Factory<Int, RequestType>() {
        override fun create() = object : PageKeyedDataSource<Int, RequestType>() {
            override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, RequestType>) {
                if (isMock()) {
                    createMockData(1, params.requestedLoadSize)?.let { callback.onResult(it, null, 2) }
                } else fetchFromRemote(1, params.requestedLoadSize) { result, nextPage ->
                    callback.onResult(result, null, nextPage)
                }

            }

            override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, RequestType>) {
                if (isMock()) {
                    val nextPage = nextMockPage(params.key)
                    if (nextPage != -1)
                        createMockData(params.key, params.requestedLoadSize)?.let { callback.onResult(it, nextPage) }
                } else fetchFromRemote(params.key, params.requestedLoadSize) { result, next ->
                    callback.onResult(result, next)
                }
            }

            override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, RequestType>) {

            }
        }
    }.map(this::convertToResult)

    protected open fun createMockData(key: Int?, requestedLoadSize: Int): MutableList<RequestType>? = null

    protected open fun isMock() = BuildConfig.MOCK_DATA

    private fun nextMockPage(key: Int) = if (key + 1 > MAX_MOCK_PAGE) -1 else key + 1

    private fun fetchFromRemote(page: Int?, pageSize: Int, callback: (MutableList<RequestType>, Int) -> Unit) {
        val call = createCall(page, pageSize) ?: throw RuntimeException("Call can not be null")
        loading(true)
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
        loading(false)
        Log.e("PAGE/SUCCESS", "${result.result?.size}  - ${result.nextPage}")
    }

    private fun onError(e: Exception) {
        error(e)
        loading(false)
        Log.e("PAGE/ERROR", e.message)
    }

    abstract fun convertToResult(it: RequestType): ResultType

}