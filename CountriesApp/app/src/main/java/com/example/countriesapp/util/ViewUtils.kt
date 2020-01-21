package com.example.countriesapp.util

import android.widget.EditText

fun EditText.toInt(): Int {
    return text.toString().toInt()
}

fun EditText.texto(): String {
    return text.toString()
}

fun EditText.clear() {
    text = null
}