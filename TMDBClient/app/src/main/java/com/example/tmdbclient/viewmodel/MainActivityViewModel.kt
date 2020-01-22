package com.example.tmdbclient.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdbclient.model.entity.Movie
import com.example.tmdbclient.model.entity.MovieDBResponse
import com.example.tmdbclient.model.entity.Status
import com.example.tmdbclient.model.service.MovieDataService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class MainActivityViewModel(private val service: MovieDataService): ViewModel() {

    private val _movie = MutableLiveData<List<Movie>>()
    val movie: LiveData<List<Movie>> = _movie

    private val _status = MutableLiveData<Status>()
    val status: LiveData<Status> = _status


    fun fetchMovies(apiKey: String) {
        _status.value = Status.Loading

        viewModelScope.launch(Dispatchers.Main) {
            var response: Response<MovieDBResponse>? = null

            withContext(Dispatchers.IO) {
                try {
                    response = service.getPopularMovie(apiKey)
                } catch (e: Exception) {
                    _status.postValue(Status.Error(e.message!!))
                }
            }

            response?.let {
                if(it.isSuccessful) {
                    _status.value = Status.Success
                    _movie.value = it.body()?.results ?: listOf()
                } else {
                    _status.value = Status.Error(it.message())
                }
            }
        }
    }
}