package com.example.tmdbclient

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide

fun Context.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun ImageView.loadImage(urlPath: String) {
    Glide.with(context)
        .load(urlPath)
        .into(this)
}