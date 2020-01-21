package com.example.countriesapp.model.entity

import com.google.gson.annotations.SerializedName

data class Country (
    @SerializedName("userId") val userId : Int,
    @SerializedName("id") val id : Int,
    @SerializedName("title") val title : String,
    @SerializedName("completed") val completed : Boolean
)