package com.example.newsapiclient.data.util

import retrofit2.Response

typealias ConvertFunction <T, R> = (T) -> R

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)

    companion object UtilsConverter {

        @JvmStatic
        fun <T> convertResponseToResource(response: Response<T>): Resource<T> {
            return if (response.isSuccessful) {
                response.body()?.let {
                    return Success(it)
                } ?: return Error("Body is null")
            } else {
                Error(response.message())
            }
        }

        @JvmStatic
        fun <T, R> convertResponseToCustomResource(
            response: Response<T>,
            convertFunction: ConvertFunction<T, R>
        ): Resource<R> {
            return if (response.isSuccessful) {
                response.body()?.let {
                    return Success(convertFunction(it))
                } ?: return Error("Body is null")
            } else {
                Error(response.message())
            }
        }
    }
}