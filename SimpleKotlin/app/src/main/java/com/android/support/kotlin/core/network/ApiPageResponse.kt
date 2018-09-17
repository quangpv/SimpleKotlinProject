package com.android.support.kotlin.core.network

import com.google.gson.annotations.SerializedName

class ApiPageResponse<T> {

    @SerializedName("data")
    val result: MutableList<T>? = null

    @SerializedName("page")
    val page = 0

    @SerializedName("per_page")
    val perPage = 0

    @SerializedName("total_pages")
    val totalPages = 0

    val nextPage: Int
        get() = page + 1
}
