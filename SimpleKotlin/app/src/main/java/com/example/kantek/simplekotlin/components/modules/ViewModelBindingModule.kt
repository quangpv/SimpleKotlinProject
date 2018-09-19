package com.example.kantek.simplekotlin.components.modules

import dagger.Module
import com.example.kantek.simplekotlin.viewmodels.MainViewModel
import android.arch.lifecycle.ViewModel
import com.android.support.kotlin.core.ViewModelKey
import dagger.multibindings.IntoMap
import dagger.Binds
import android.arch.lifecycle.ViewModelProvider
import com.android.support.kotlin.core.ViewModelFactory

@Module
abstract class ViewModelBindingModule {
    @Binds
    abstract fun bindsViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindsMainViewModel(viewModel: MainViewModel): ViewModel
}
