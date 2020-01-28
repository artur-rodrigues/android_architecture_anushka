package com.anushka.viewmodeldemo1.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel(initial: Int): ViewModel() {

    private val sum = MutableLiveData<Int>()
    fun getSum(): LiveData<Int> = sum

    init {
        sum.value = initial
    }

    fun makeSum(num: Int) {
        sum.apply {
            value = value?.plus(num)
        }
    }
}