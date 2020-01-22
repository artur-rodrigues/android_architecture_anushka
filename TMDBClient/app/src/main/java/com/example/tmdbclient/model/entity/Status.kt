package com.example.tmdbclient.model.entity

sealed class Status {
    object Loading : Status()
    object Success : Status()
    class Error(val error: String): Status()
}