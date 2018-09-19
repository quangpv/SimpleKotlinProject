package com.example.kantek.simplekotlin.views

import android.os.Bundle
import com.android.support.kotlin.core.base.BaseActivity
import com.android.support.kotlin.core.LayoutId
import com.android.support.kotlin.core.livedata.call
import com.android.support.kotlin.core.livedata.observe
import com.example.kantek.simplekotlin.viewmodels.MainViewModel
import com.example.kantek.simplekotlin.R
import com.example.kantek.simplekotlin.models.User
import kotlinx.android.synthetic.main.activity_main.*

@LayoutId(R.layout.activity_main)
class MainActivity : BaseActivity<MainViewModel>() {
    private var mUser: User? = null
    private lateinit var mUserAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mUserAdapter = UserAdapter(recvUser)
        mViewModel.users.observe(this) { mUserAdapter.submitList(it) }
        mViewModel.user.observe(this) { this.mUser = it!!; txtUser.text = it.toString() }

        mViewModel.loading.observe(this, this::showLoading)
        mViewModel.registrySuccess.observe(this, this::showSuccess)
        mViewModel.registryError.observe(this, this::showError)
        mViewModel.error.observe(this, this::showError)

        txtUser.setOnClickListener { mViewModel.registry.value = mUser }
        mViewModel.userId.value = 2
        mViewModel.download.value = 2
        mViewModel.downloadStatus.observe(this) { showSuccess(it) }
        mViewModel.zipStatus.observe(this) { showSuccess(it) }
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
