package com.example.kantek.simplekotlin

import android.arch.lifecycle.*
import com.android.support.kotlin.core.base.BaseViewModel
import com.android.support.kotlin.core.livedata.*
import java.util.*
import javax.inject.Inject

class MainViewModel @Inject constructor(userRepository: UserRepository) : BaseViewModel() {

    var refresh = SingleLiveEvent<Void>()
    var userId = SingleLiveEvent<Int>()
    var registry = SingleLiveEvent<User>()
    val registryError = SingleLiveEvent<Exception>()

    var name: LiveData<String> = refresh
            .switchTo {
                userRepository.loadUsers()
                        .notifyLoadingTo(loading)
                        .notifyErrorTo(error)
            }
            .map { it!!.reduce { sum, next -> "$sum $next ${Random().nextInt()}" } }

    var user = userId
            .switchTo { it ->
                it.notNull {
                    userRepository.loadUser(it)
                            .notifyErrorTo(error)
                            .notifyLoadingTo(loading)
                }
            }

    var registrySuccess = registry
            .switchTo { it ->
                it.notNull {
                    userRepository.registry(it)
                            .notifyErrorTo(registryError)
                            .notifyLoadingTo(loading)
                }
            }
            .filter { it != null }
            .map { it.toString() }

    init {
        refresh.call()
    }
}
