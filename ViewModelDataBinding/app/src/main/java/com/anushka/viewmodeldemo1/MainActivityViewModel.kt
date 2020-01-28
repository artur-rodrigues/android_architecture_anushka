package com.anushka.viewmodeldemo1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel() {
    private val count = MutableLiveData<Int>()
    fun getCount(): LiveData<Int> = count

    init {
        count.value = 0
    }

    fun updateCount(){
        count.apply {
            value = value?.plus(1)
        }
    }
}