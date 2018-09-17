package com.example.kantek.simplekotlin

import com.android.support.kotlin.core.network.ApiResponse
import com.android.support.kotlin.core.network.PageByIndexRequestBound
import com.android.support.kotlin.core.network.PageCachedRequestBound
import com.android.support.kotlin.core.network.RequestBound
import retrofit2.Call
import javax.inject.Inject

class UserRepository @Inject constructor(private val apiService: ApiService,
                                         appDatabase: AppDatabase) {
    private val userDao = appDatabase.userDao()

    fun loadUsers() = object : RequestBound<MutableList<User>, MutableList<User>>() {
        override fun isMock() = false
        override fun convertToResult(result: MutableList<User>?) = result
        override fun createCall(): Call<ApiResponse<MutableList<User>>>? = apiService.users
    }.asLiveData()

    fun loadCachedUsers() = object : PageCachedRequestBound<User, List<User>, List<User>>(4) {
        override fun convertToResult(result: List<User>?) = result
        override fun isMock() = false
        override fun createMockData(): List<User>? {
            val users: MutableList<User> = ArrayList()
            for (i in 0..50) {
                var user = User()
                users.add(user)
                user.id = i
                user.firstName = "$i Name"

            }
            return users
        }

        override fun createCall() = apiService.cachedUsers
        override fun createDataSource() = userDao.getUsers()
        override fun saveCallResult(result: List<User>?) {
            userDao.save(result)
        }
    }.asLiveData()

    fun loadPageUsers() = object : PageByIndexRequestBound<User, User>(4) {
        override fun createMockData(key: Int?, requestedLoadSize: Int): MutableList<User>? {
            val users: MutableList<User> = ArrayList()
            if (key != null) {
                for (i in ((key - 1) * requestedLoadSize)..(key * requestedLoadSize)) {
                    val user = User()
                    users.add(user)
                    user.id = i
                    user.firstName = "$i Name"

                }
            }
            return users
        }

        override fun createCall(page: Int?, pageSize: Int) = apiService.getPageUsers(page, pageSize)
        override fun convertToResult(it: User) = it
    }.asLiveData()

    fun loadUser(id: Int) = object : RequestBound<User, User>() {
        override fun convertToResult(result: User?) = result
        override fun isMock() = false
        override fun createCall() = apiService.getUser(id)
    }.asLiveData()

    fun registry(it: User) = object : RequestBound<User, User>() {
        override fun createMockData() = it
        override fun createCall() = null
        override fun convertToResult(result: User?) = result
    }.asLiveData()
}
