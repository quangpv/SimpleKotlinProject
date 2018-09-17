package com.android.support.kotlin.core.base

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.view.WindowManager
import android.view.MotionEvent

class BaseDialog(context: Context) : Dialog(context) {
    private var mCancelable: Boolean

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        mCancelable = true
    }

    fun requestFullScreen() {
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT)
    }

    override fun setCancelable(flag: Boolean) {
        super.setCancelable(flag)
        mCancelable = flag
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (mCancelable) {
            if (event.action == MotionEvent.ACTION_UP) dismiss()
            return true
        }
        return super.onTouchEvent(event)
    }
}