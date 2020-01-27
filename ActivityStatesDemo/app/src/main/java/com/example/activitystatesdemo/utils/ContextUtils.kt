package com.example.activitystatesdemo.utils

import android.content.Context
import android.widget.Toast

fun Context.toast(msg: String, time: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, msg, time).show()
}