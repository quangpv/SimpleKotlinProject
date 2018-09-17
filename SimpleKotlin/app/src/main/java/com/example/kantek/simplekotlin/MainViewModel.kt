package com.example.kantek.simplekotlin

import com.android.support.kotlin.core.base.BaseViewModel
import com.android.support.kotlin.core.livedata.*
import javax.inject.Inject

class MainViewModel @Inject constructor(userRepository: UserRepository) : BaseViewModel() {

    var refresh = SingleLiveEvent<Void>()
    var userId = SingleLiveEvent<Int>()
    var registry = SingleLiveEvent<User>()
    val registryError = SingleLiveEvent<Exception>()

    var users = refresh
            .switchTo {
                userRepository.loadPageUsers()
                        .notifyLoadingTo(loading)
                        .notifyErrorTo(error)
            }

    var user = userId
            .switchTo { it ->
                it.nonNull {
                    userRepository.loadUser(it)
                            .notifyErrorTo(error)
                            .notifyLoadingTo(loading)
                }
            }

    var registrySuccess = registry
            .switchTo { it ->
                it.nonNull {
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
