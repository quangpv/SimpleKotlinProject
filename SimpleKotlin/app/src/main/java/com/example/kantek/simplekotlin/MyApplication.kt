package com.example.kantek.simplekotlin

import com.example.kantek.simplekotlin.components.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class MyApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        sInstance = this
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
            DaggerAppComponent.builder().create(this)

    companion object {
        private lateinit var sInstance: MyApplication
        val instance: MyApplication
            get() = sInstance
    }
}