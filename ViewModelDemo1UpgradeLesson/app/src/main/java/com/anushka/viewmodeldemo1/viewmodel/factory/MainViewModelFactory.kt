package com.anushka.viewmodeldemo1.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anushka.viewmodeldemo1.viewmodel.MainActivityViewModel
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val initial: Int): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass == MainActivityViewModel::class.java) {
            return MainActivityViewModel(initial) as T
        }

        throw IllegalArgumentException("God dammit!")
    }
}