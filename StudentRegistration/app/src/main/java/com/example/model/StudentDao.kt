package com.example.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface StudentDao {

    @Insert
    fun insertStudent(student: Student): Completable

    @Delete
    fun deleteStudent(student: Student): Completable

    @Query("SELECT * FROM student")
    fun findAll(): Single<MutableList<Student>>
}