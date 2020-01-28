package com.anushka.viewmodeldemo2

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel(startingTotal: Int) : ViewModel() {
    val total = MutableLiveData<Int>()
//    var num: String = ""

    init {
        total.value = startingTotal
    }

    fun setTotal(num: String) {
        total.apply {
            value = value?.plus(num.toInt())
        }
    }

    /*fun setTotal() {
        total.apply {
            value = value?.plus(num.toInt())
        }
    }*/
}