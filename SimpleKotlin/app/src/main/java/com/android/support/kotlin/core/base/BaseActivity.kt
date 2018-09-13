package com.android.support.kotlin.core.base

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.android.support.kotlin.core.ClassHelper

abstract class BaseActivity<VM : ViewModel> : AppCompatActivity() {

    protected lateinit var mViewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProviders
                .of(this)
                .get(ClassHelper.getFirstGenericParameter(this))
        setContentView(ClassHelper.getLayoutId(this))
    }
}
