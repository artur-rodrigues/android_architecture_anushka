package com.example.tmdbclient.model.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    lateinit var retrofit: Retrofit

    private fun getInstance(): Retrofit {
        if(! ::retrofit.isInitialized) {
            retrofit = Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        return retrofit
    }

    fun getMovieService(): MovieDataService {
        return getInstance().create(MovieDataService::class.java)
    }
}