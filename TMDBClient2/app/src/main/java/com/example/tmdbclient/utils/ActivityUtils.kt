package com.example.tmdbclient.utils

import android.app.Activity
import android.content.Intent
import android.os.Bundle

fun Activity.goToActivity(
    clazz: Class<out Activity>,
    bundle: Bundle? = null,
    requestCode: Int = -1
) {
    val intent = Intent(this, clazz)

    bundle?.let {
        intent.putExtras(it)
    }

    if (requestCode == -1) {
        startActivity(intent)
    } else {
        startActivityForResult(intent, requestCode);
    }
}