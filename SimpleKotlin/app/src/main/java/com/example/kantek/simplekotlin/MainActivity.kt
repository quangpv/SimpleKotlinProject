package com.example.kantek.simplekotlin

import android.os.Bundle
import com.android.support.kotlin.core.base.BaseActivity
import com.android.support.kotlin.core.LayoutId
import com.android.support.kotlin.core.livedata.call
import com.android.support.kotlin.core.livedata.observe
import kotlinx.android.synthetic.main.activity_main.*

@LayoutId(R.layout.activity_main)
class MainActivity : BaseActivity<MainViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel.name.observe(this) { txtName.text = it }
        mViewModel.dataSilent.observe(this) { txtSilent.text = it.toString() }
        mViewModel.loading.observe(this, this::showLoading)
        mViewModel.registrySuccess.observe(this, this::showSuccess)
        mViewModel.registryError.observe(this, this::showError)
        mViewModel.error.observe(this, this::showError)

        txtName.setOnClickListener { mViewModel.refresh.call() }
        txtSilent.setOnClickListener { mViewModel.registry.call() }
        mViewModel.userId.value = 2
    }

    private fun showSuccess(it: String?) {
        txtError.text = it
    }

    private fun showError(it: Exception?) {
        txtLoading.text = it!!.message
    }

    private fun showLoading(it: Boolean?) {
        txtLoading.text = if (it!!) "loading" else "hide-loading"
    }
}
