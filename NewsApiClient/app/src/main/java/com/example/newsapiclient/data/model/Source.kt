package com.example.newsapiclient.data.model


import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "source")
data class Source(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)