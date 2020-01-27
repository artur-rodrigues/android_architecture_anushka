package com.example.activitystatesdemo.utils

import android.content.Context
import android.util.Log
import android.widget.Toast

fun Context.toast(msg: String, time: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, msg, time).show()
}

fun Context.log(msg: String, tag: String = "LIFECYCLE", showStars: Boolean = true) {
    Log.i(tag, if(showStars) "******************* $msg *******************" else msg)
}