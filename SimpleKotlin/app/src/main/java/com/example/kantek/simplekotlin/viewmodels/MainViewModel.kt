package com.example.kantek.simplekotlin.viewmodels

import androidx.work.*
import com.android.support.kotlin.core.base.BaseViewModel
import com.android.support.kotlin.core.livedata.*
import com.example.kantek.simplekotlin.helpers.DownloadWorker
import com.example.kantek.simplekotlin.helpers.UnZipWorker
import com.example.kantek.simplekotlin.models.User
import com.example.kantek.simplekotlin.repositories.UserRepository
import javax.inject.Inject

class MainViewModel @Inject constructor(userRepository: UserRepository,
                                        workManager: WorkManager) : BaseViewModel() {

    var refresh = SingleLiveEvent<Void>()
    var userId = SingleLiveEvent<Int>()
    var registry = SingleLiveEvent<User>()
    val registryError = SingleLiveEvent<Exception>()
    val download = SingleLiveEvent<Int>()
    val zipStatus = SingleLiveEvent<String>()

    var users = refresh
            .switchTo {
                userRepository.loadPageUsers()
                        .notifyLoadingTo(loading)
                        .notifyErrorTo(error)
                        .asLiveData()
            }

    var user = userId
            .switchTo { it ->
                it.nonNull {
                    userRepository.loadUser(it)
                            .notifyErrorTo(error)
                            .notifyLoadingTo(loading)
                            .asLiveData()
                }
            }

    var registrySuccess = registry
            .switchTo { it ->
                it.nonNull {
                    userRepository.registry(it)
                            .notifyErrorTo(registryError)
                            .notifyLoadingTo(loading)
                            .asLiveData()
                }
            }
            .filter { it != null }
            .map { it.toString() }

    var downloadStatus = download
            .switchTo { it ->
                val downloadWorker = DownloadWorker.oneTimeWork(it!!)
                val unZipWorker = UnZipWorker.oneTimeWork()
                workManager
                        .beginWith(downloadWorker)
                        .then(unZipWorker)
                        .enqueue()
                workManager.getStatusById(unZipWorker.id)
                        .filter {
                            val state = it?.state
                            state == State.SUCCEEDED
                                    || state == State.RUNNING
                                    || state == State.FAILED
                        }
                        .map { "ZIP ${it?.state?.name}" }
                        .forwardTo(zipStatus)
                workManager.getStatusById(downloadWorker.id)
            }
            .map { "DOWNLOAD Index ${it?.outputData?.getInt("data", 0)}" }

    init {
        refresh.call()
    }
}

