package com.example.kantek.simplekotlin.components.modules

import dagger.Module
import com.example.kantek.simplekotlin.MainActivity
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBindingModule {
    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity
}
