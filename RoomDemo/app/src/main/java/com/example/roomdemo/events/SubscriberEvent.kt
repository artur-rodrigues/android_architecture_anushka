package com.example.roomdemo.events

class SubscriberEvent<out T>(private val content: T) {

    var hasBeenHandle = false
        private set

    fun getContentIfNotHandle(): T? {
        return if (hasBeenHandle) {
            null
        } else {
            hasBeenHandle = true
            content
        }
    }

    fun peekContent() = content
}