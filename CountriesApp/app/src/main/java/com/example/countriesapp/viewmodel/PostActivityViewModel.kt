package com.example.countriesapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.countriesapp.model.entity.Result
import com.example.countriesapp.model.service.CountryDataService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PostActivityViewModel(private val service: CountryDataService): ViewModel() {

    private val _status = MutableLiveData<Result>()
    val status: LiveData<Result> = _status

    fun fetchSomeThing() {
        _status.value = Result.Loading

        viewModelScope.launch(Dispatchers.IO) {

        }
    }
}