package com.example.countriesapp.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.countriesapp.model.service.CountryDataService
import com.example.countriesapp.viewmodel.FindActivityViewModel
import com.example.countriesapp.viewmodel.MainActivityViewModel
import com.example.countriesapp.viewmodel.PostActivityViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val service: CountryDataService): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when(modelClass) {
            MainActivityViewModel::class.java -> MainActivityViewModel(service)
            FindActivityViewModel::class.java -> FindActivityViewModel(service)
            PostActivityViewModel::class.java -> PostActivityViewModel(service)
            else -> throw IllegalArgumentException("ViewModel Not Found")
        } as T
    }
}