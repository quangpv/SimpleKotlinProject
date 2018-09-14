package com.example.kantek.simplekotlin

import com.android.support.kotlin.core.network.ApiResponse
import com.android.support.kotlin.core.network.PageByIndexRequestBound
import com.android.support.kotlin.core.network.RequestBound
import retrofit2.Call
import javax.inject.Inject

class UserRepository @Inject constructor(private var apiService: ApiService) {

    fun loadUsers() = object : RequestBound<MutableList<User>, MutableList<User>>() {
        override fun isMock() = false
        override fun convertToResult(result: MutableList<User>?): MutableList<User>? = result
        override fun createCall(): Call<ApiResponse<MutableList<User>>>? = apiService.users
    }.asLiveData()

    fun loadPageUsers() = object : PageByIndexRequestBound<User, User>(4) {
        override fun createCall(page: Int?, pageSize: Int) = apiService.getPageUsers(page, pageSize)

        override fun convertToResult(it: User) = it
    }

    fun loadUser(id: Int) = object : RequestBound<User, User>() {
        override fun convertToResult(result: User?): User? = result
        override fun isMock() = false
        override fun createCall(): Call<ApiResponse<User>>? = apiService.getUser(id)
    }.asLiveData()

    fun registry(it: User) = object : RequestBound<User, User>() {
        override fun createMockData(): User? = it
        override fun createCall(): Call<ApiResponse<User>>? = null
        override fun convertToResult(result: User?): User? = result
    }.asLiveData()
}
