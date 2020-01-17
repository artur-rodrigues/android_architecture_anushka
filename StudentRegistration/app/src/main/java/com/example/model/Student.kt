package com.example.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "student")
data class Student(
    @PrimaryKey(autoGenerate = true)
    var studentId: Long,
    var name: String,
    var email: String,
    var country: String,
    var registeredTime: String
) 