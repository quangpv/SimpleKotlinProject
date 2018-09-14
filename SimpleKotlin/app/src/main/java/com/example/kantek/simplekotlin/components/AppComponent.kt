package com.example.kantek.simplekotlin.components


import com.example.kantek.simplekotlin.MyApplication
import com.example.kantek.simplekotlin.components.modules.ActivityBindingModule
import com.example.kantek.simplekotlin.components.modules.AppModule
import com.example.kantek.simplekotlin.components.modules.ViewModelBindingModule

import javax.inject.Singleton

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class,
    AppModule::class,
    ViewModelBindingModule::class,
    ActivityBindingModule::class])
interface AppComponent : AndroidInjector<MyApplication> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<MyApplication>()
}
