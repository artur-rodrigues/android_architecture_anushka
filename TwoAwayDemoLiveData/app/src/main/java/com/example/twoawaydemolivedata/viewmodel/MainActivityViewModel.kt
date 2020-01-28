package com.example.twoawaydemolivedata.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel: ViewModel() {

    val userName = MutableLiveData<String>()

    init {
        userName.value = "Leeroy Genkins"
    }
}