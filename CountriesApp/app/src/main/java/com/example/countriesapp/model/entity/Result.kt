package com.example.countriesapp.model.entity

sealed class Result {
    object Loading : Result()
    object Success : Result()
    class Error(val error: String): Result()
}