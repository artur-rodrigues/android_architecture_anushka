package com.example.activitystatesdemo.utils

import android.util.Log
import androidx.lifecycle.LifecycleObserver

fun LifecycleObserver.log(msg: String, tag: String = "LIFECYCLE", showStars: Boolean = true) {
    Log.i(tag, if(showStars) "******************* $msg *******************" else msg)
}