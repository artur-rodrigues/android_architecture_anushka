package com.example.countriesapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.countriesapp.model.entity.Country
import com.example.countriesapp.model.entity.Result
import com.example.countriesapp.model.service.CountryDataService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivityViewModel(private val service: CountryDataService): ViewModel() {

    private val _countries = MutableLiveData<ArrayList<Country>>()
    private val _status = MutableLiveData<Result>()

    val countries: LiveData<ArrayList<Country>> = _countries
    val status: LiveData<Result> = _status

    /**
     * Versão que trabalha mudando o contexto da coroutine para mostrar
     * os dados na tela
     */
    /*fun fetchCountries() {
        _status.value = Result.Loading
        
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = service.getCountries()

                if(response.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        _status.value = Result.Success
                        _countries.value = response.body()
                    }
                } else {
                    setErrorStatus(response.message())
                }
            } catch (e: Exception) {
                setErrorStatus(e)
            }
        }
    }

    private suspend fun setErrorStatus(error: String) {
        withContext(Dispatchers.Main) {
            _status.value = Result.Error(error)
        }
    }*/

    /**
     * Versão que coloca o funcionamento no Dispatcher.IO e dá um post nos livedata
     * sem mudar o contexto da coroutine.
     */
    fun fetchCountries() {
        _status.value = Result.Loading

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = service.getCountries()

                if(response.isSuccessful) {
                    _status.postValue(Result.Success) 
                    _countries.postValue(response.body())
                } else {
                    _status.postValue(Result.Error(response.message()))
                }
            } catch (e: Exception) {
                _status.postValue(Result.Error(e.message!!))
            }
        }
    }


}