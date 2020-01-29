package com.anushka.navdemo5.utils

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

fun Context.toast(msg: String, time: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, msg, time).show()
}

fun Context.toast(@StringRes msg: Int, time: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, msg, time).show()
}