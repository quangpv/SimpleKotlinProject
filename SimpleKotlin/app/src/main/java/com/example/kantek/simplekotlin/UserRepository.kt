package com.example.kantek.simplekotlin

import com.android.support.kotlin.core.network.ApiResponse
import com.android.support.kotlin.core.network.RequestBound
import retrofit2.Call

class UserRepository {
    var apiService = MyApplication.instance.apiService

    fun loadUsers() = object : RequestBound<List<String>, List<String>>() {
        override fun createMockData(): List<String>? {
            val users: MutableList<String> = mutableListOf()
            for (i in 0..10) {
                users.add("User $i")
            }
            return users.filter { it.contains(Regex("[1-5]")) }
                    .sortedByDescending { it }
                    .map { "$it Mapped" }
        }

        override fun convertToResult(result: List<String>?): List<String>? = result

        override fun createCall(): Call<ApiResponse<List<String>>>? = null
    }.asLiveData()

    fun loadUser(id: Int) = object : RequestBound<User, User>() {
        override fun convertToResult(result: User?): User? = result
        override fun isMock() = false
        override fun createCall(): Call<ApiResponse<User>>? = apiService.getUser(id)
    }.asLiveData()

    fun registry() = object : RequestBound<User, User>() {
        override fun createMockData(): User? = null

        override fun createCall(): Call<ApiResponse<User>>? = null

        override fun convertToResult(result: User?): User? = result
    }.asLiveData()
}
