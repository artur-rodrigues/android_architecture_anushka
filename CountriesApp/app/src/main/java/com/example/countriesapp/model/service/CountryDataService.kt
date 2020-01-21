package com.example.countriesapp.model.service

import com.example.countriesapp.model.entity.Country
import com.example.countriesapp.model.entity.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CountryDataService {

    @GET("todos")
    suspend fun getCountries(): Response<ArrayList<Country>>

    @GET("todos/{id}")
    suspend fun getCountry(@Path("id") id: Int): Response<Country>

    @POST("posts")
    suspend fun getUser(@Body user: User): Response<User>
}