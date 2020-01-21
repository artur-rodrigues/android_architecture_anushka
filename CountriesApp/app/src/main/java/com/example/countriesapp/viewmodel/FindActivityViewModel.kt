package com.example.countriesapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.countriesapp.model.entity.Country
import com.example.countriesapp.model.entity.Result
import com.example.countriesapp.model.service.CountryDataService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class FindActivityViewModel(private val service: CountryDataService): ViewModel() {

    private val _country = MutableLiveData<Country>()
    val country: LiveData<Country> = _country

    private val _status = MutableLiveData<Result>()
    val status: LiveData<Result> = _status

    fun fetchCountry(countryId: Int) {
        _status.value = Result.Loading

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = service.getCountry(countryId)

                if(response.isSuccessful) {
                    _status.postValue(Result.Success)
                    _country.postValue(response.body())
                } else {
                    _status.postValue(Result.Error(response.message()))
                }
            } catch (e: Exception) {
                _status.postValue(Result.Error(e.message!!))
            }
        }
    }
}