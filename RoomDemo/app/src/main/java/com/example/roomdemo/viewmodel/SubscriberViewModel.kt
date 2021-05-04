package com.example.roomdemo.viewmodel

import android.util.Patterns
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomdemo.db.Subscriber
import com.example.roomdemo.db.SubscriberRepository
import com.example.roomdemo.events.SubscriberEvent
import kotlinx.coroutines.launch

class SubscriberViewModel(private val subscriberRepository: SubscriberRepository) : ViewModel(),
    Observable {

    val subscribers = subscriberRepository.subscribers

    private var isUpdateOrDelete = false
    private lateinit var subscriberToUpdateOrDelete: Subscriber

    @Bindable
    val inputName = MutableLiveData<String>()

    @Bindable
    val inputEmail = MutableLiveData<String>()

    val saveOrUpdateButtonText = MutableLiveData<String>()

    val clearAllDeleteButtonText = MutableLiveData<String>()

    private val statusMessage = MutableLiveData<SubscriberEvent<String>>()

    val message: LiveData<SubscriberEvent<String>>
        get() = statusMessage

    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllDeleteButtonText.value = "Clear All"
    }

    fun saveOrUpdate() {
        when {
            inputName.value == null -> notifyUser("Please enter Subscriber's name")
            inputEmail.value == null -> notifyUser("Please enter Subscriber's email")
            !Patterns.EMAIL_ADDRESS.matcher(inputEmail.value!!).matches() ->
                notifyUser("Please enter a correct email")
            else -> saveOrUpdateSubscriber()
        }
    }

    private fun saveOrUpdateSubscriber() {
        if (isUpdateOrDelete) {
            subscriberToUpdateOrDelete.run {
                name = inputName.value!!
                email = inputEmail.value!!
            }
            updateSubscriber(subscriberToUpdateOrDelete)
        } else {
            val name = inputName.value!!
            val email = inputEmail.value!!
            insertSubscriber(Subscriber(0, name, email))

            inputName.value = null
            inputEmail.value = null
        }
    }

    fun clearAllOrDelete() {
        if (isUpdateOrDelete) {
            deleteSubscriber(subscriberToUpdateOrDelete)
        } else {
            clearAll()
        }
    }

    fun initUpdateAndDelete(subscriber: Subscriber) {
        subscriber.run {
            inputName.value = name
            inputEmail.value = email
            isUpdateOrDelete = true
            subscriberToUpdateOrDelete = this
        }

        saveOrUpdateButtonText.value = "Update"
        clearAllDeleteButtonText.value = "Delete"
    }

    private fun notifyUser(msg: String) {
        statusMessage.value = SubscriberEvent(msg)
    }

    private fun insertSubscriber(subscriber: Subscriber) {
        viewModelScope.launch {
            val rowId = subscriberRepository.insert(subscriber)
            if (rowId > -1) {
                notifyUser("Subscriber add successfully")
            } else {
                notifyError()
            }
        }
    }

    private fun updateSubscriber(subscriber: Subscriber) {
        viewModelScope.launch {
            val rowCount = subscriberRepository.update(subscriber)

            if (rowCount > 0) {
                notifyUser("$rowCount Row updated successfully")
            } else {
                notifyError()
            }
            resetFields()
        }
    }

    private fun notifyError() {
        notifyUser("Error Occurred")
    }

    private fun deleteSubscriber(subscriber: Subscriber) {
        viewModelScope.launch {
            val rowCount = subscriberRepository.delete(subscriber)
            if (rowCount > 0) {
                notifyUser("$rowCount Row deleted successfully")
            } else {
                notifyError()
            }
            resetFields()
        }
    }

    private fun resetFields() {
        inputName.value = null
        inputEmail.value = null
        isUpdateOrDelete = false
        saveOrUpdateButtonText.value = "Save"
        clearAllDeleteButtonText.value = "Clear All"
    }

    private fun clearAll() {
        viewModelScope.launch {
            val rowCount = subscriberRepository.deleteAll()
            if (rowCount > 0) {
                notifyUser("$rowCount Row deleted successfully")
            } else {
                notifyError()
            }
        }
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}