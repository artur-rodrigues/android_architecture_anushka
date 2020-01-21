package com.example.countriesapp.util

import android.widget.EditText

fun EditText.toInt(): Int {
    return text.toString().toInt()
}