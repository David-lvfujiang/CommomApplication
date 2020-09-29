package com.cxz.wanandroid.ext

import android.app.Activity
import android.content.Context

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Checkable
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

import com.google.android.material.snackbar.Snackbar

import com.nnmzkj.common.view.CustomToast
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by chenxz on 2018/4/22.
 */
/**
 * Log
 */


fun Fragment.showToast(content: String) {
    CustomToast(this?.activity?.applicationContext,Toast.LENGTH_SHORT).show(content)
}

fun Context.showToast(content: String) {
    CustomToast(this, Toast.LENGTH_SHORT).show(content)
}

fun Fragment.showSuccessToast(content: String) {
    CustomToast(this?.activity?.applicationContext,Toast.LENGTH_SHORT).success(content)
}

fun Context.showSuccessToast(content: String) {
    CustomToast(this, Toast.LENGTH_SHORT).success(content)
}

fun Fragment.showErrorToast(content: String) {
    CustomToast(this?.activity?.applicationContext,Toast.LENGTH_SHORT).error(content)
}

fun Context.showErrorToast(content: String) {
    CustomToast(this, Toast.LENGTH_SHORT).error(content)
}

