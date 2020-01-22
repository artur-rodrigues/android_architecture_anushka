package com.example.tmdbclient.util

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.tmdbclient.R

fun EditText.toInt(): Int {
    return text.toString().toInt()
}

fun EditText.texto(): String {
    return text.toString()
}

fun EditText.clear() {
    text = null
}

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun getProgressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        colorFilter = PorterDuffColorFilter(
            ContextCompat.getColor(context, R.color.colorAccent),
            PorterDuff.Mode.SRC_IN)
        start()
    }
}

fun ImageView.loadImage(url: String?, progressDrawable: CircularProgressDrawable? = null) {
    url?.let {
        val options = RequestOptions()
            .placeholder(progressDrawable ?: getProgressDrawable(context))
            .error(R.mipmap.ic_launcher)

        Glide.with(context)
            .setDefaultRequestOptions(options)
            .load(it)
            .into(this)
    }
}

@BindingAdapter("android:imageUrl")
fun loadImageIntoImageView(view: ImageView, url: String?) {
    view.loadImage(url)
}