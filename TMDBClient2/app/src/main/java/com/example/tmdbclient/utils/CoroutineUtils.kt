package com.example.tmdbclient.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun runInIoAndForget(executable: suspend () -> Unit) {
    CoroutineScope(Dispatchers.IO).launch {
        executable()
    }
}

fun <T> runInIoForResult(executable: suspend () -> T, collect: (T) -> Unit) {
    CoroutineScope(Dispatchers.IO).launch {
        val result = executable()
        withContext(Dispatchers.Main) {
            collect(result)
        }
    }
}