package com.example.tmdbclient.model.service

import com.example.tmdbclient.model.entity.MovieDBResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieDataService {

    @GET("movie/popular")
    suspend fun getPopularMovie(@Query("api_key") api: String): Response<MovieDBResponse>

    @GET("movie/popular")
    suspend fun getPagedPopularMovie(@Query("api_key") api: String,
                                     @Query("page") page: Long): Response<MovieDBResponse>
}