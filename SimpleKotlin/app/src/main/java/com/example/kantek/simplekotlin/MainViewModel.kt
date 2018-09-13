package com.example.kantek.simplekotlin

import android.arch.lifecycle.*
import com.android.support.kotlin.core.base.BaseViewModel
import com.android.support.kotlin.core.livedata.*
import java.util.*

class MainViewModel(
        userRepository: UserRepository = UserRepository(),
        var refresh: SingleLiveEvent<Void> = SingleLiveEvent(),
        var userId: SingleLiveEvent<Int> = SingleLiveEvent(),
        var registry: SingleLiveEvent<Void> = SingleLiveEvent(),
        val registryError: ExtendLiveData<Exception> = SingleLiveEvent()
) : BaseViewModel() {

    var name: LiveData<String> = refresh
            .switchTo {
                userRepository.loadUsers()
                        .notifyLoadingTo(loading)
                        .notifyErrorTo(error)
            }
            .map { it!!.reduce { sum, next -> "$sum $next ${Random().nextInt()}" } }

    var dataSilent = userId
            .switchTo {
                userRepository.loadUser(it!!)
                        .notifyErrorTo(error)
                        .notifyLoadingTo(loading)
            }

    var registrySuccess = registry
            .switchTo {
                userRepository.registry()
                        .notifyErrorTo(registryError)
                        .notifyLoadingTo(loading)
            }
            .filter { it != null }
            .map { it.toString() }

    init {
        refresh.call()
    }
}
