package com.example.tmdbclient.view.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tmdbclient.model.service.MovieDataService
import com.example.tmdbclient.viewmodel.MainActivityViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val service: MovieDataService): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when(modelClass) {
            MainActivityViewModel::class.java -> MainActivityViewModel(service)
            else -> throw IllegalArgumentException("ViewModel Not Found")
        } as T
    }
}