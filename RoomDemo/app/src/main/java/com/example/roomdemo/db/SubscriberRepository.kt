package com.example.roomdemo.db

class SubscriberRepository(private val dao: SubscriberDAO) {

    val subscribers = dao.getAll()

    suspend fun insert(subscriber: Subscriber): Long = dao.insert(subscriber)

    suspend fun update(subscriber: Subscriber): Int = dao.update(subscriber)

    suspend fun delete(subscriber: Subscriber): Int = dao.delete(subscriber)

    suspend fun deleteAll(): Int = dao.deleteAll()
}