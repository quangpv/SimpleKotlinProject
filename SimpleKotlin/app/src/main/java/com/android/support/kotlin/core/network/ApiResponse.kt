package com.android.support.kotlin.core.network

import com.google.gson.annotations.SerializedName

class ApiResponse<T> {

    @SerializedName("success")
    var success: Boolean = true

    @SerializedName("message")
    var message: String? = null

    @SerializedName("data")
    var result: T? = null

    fun isSuccess(): Boolean {
        return success
    }

}
