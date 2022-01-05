package com.example.news

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.annotation.ColorRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.gyf.immersionbar.ImmersionBar

open class BaseActivity : AppCompatActivity() {

    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBarColor(R.color.purple_700)
        context = this

    }

    private fun setBarColor(@ColorRes barColor: Int) {
        ImmersionBar.with(this)
            // 重置
            .reset()
            // 狀態欄與Layout不重疊
            .fitsSystemWindows(true)
            .barColor(barColor)
            // 自動調整狀態欄/導航欄字體顏色
            .autoDarkModeEnable(true)
            .init()
    }
}