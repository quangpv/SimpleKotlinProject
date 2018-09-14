package com.android.support.kotlin.core.base

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.android.support.kotlin.core.ClassHelper
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity<VM : ViewModel> : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    protected lateinit var mViewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(ClassHelper.getFirstGenericParameter(this))
        setContentView(ClassHelper.getLayoutId(this))
    }
}
