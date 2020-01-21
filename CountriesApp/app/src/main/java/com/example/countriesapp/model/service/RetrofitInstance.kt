package com.example.countriesapp.model.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    lateinit var retrofit: Retrofit

    private fun getInstance(): Retrofit {
        if(! ::retrofit.isInitialized) {
            retrofit = Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        return retrofit
    }

    fun getCountryService(): CountryDataService {
        return getInstance().create(CountryDataService::class.java)
    }
}