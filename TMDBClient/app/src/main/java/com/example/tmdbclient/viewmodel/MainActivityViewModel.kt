package com.example.tmdbclient.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.tmdbclient.model.datasource.MovieDataSource
import com.example.tmdbclient.model.datasource.factory.MovieDataSourceFactory
import com.example.tmdbclient.model.entity.Movie
import com.example.tmdbclient.model.entity.MovieDBResponse
import com.example.tmdbclient.model.entity.Status
import com.example.tmdbclient.model.service.MovieDataService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class MainActivityViewModel(private val service: MovieDataService): ViewModel() {

    private val movies = MutableLiveData<List<Movie>>()
    fun getMovie(): LiveData<List<Movie>> = movies

    private val status = MutableLiveData<Status>()
    fun getStatus(): LiveData<Status> = status

    lateinit var moviesPagedList: LiveData<PagedList<Movie>>

    private val factory: MovieDataSourceFactory by lazy {
        MovieDataSourceFactory(service)
    }

    val dataSource: LiveData<MovieDataSource> by lazy {
        factory.getDataSource()
    }

    init {
        setUpPageList()
    }

    private fun setUpPageList() {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(10)
            .setPageSize(20)
            .setPrefetchDistance(4)
            .build()

        val executor = Executors.newFixedThreadPool(5)

        moviesPagedList = LivePagedListBuilder<Long, Movie>(factory, config)
            .setFetchExecutor(executor)
            .build()
    }

    /**
     * Código antigo
     */
    /*fun fetchMovies(apiKey: String) {
        status.value = Status.Loading

        viewModelScope.launch(Dispatchers.Main) {
            var response: Response<MovieDBResponse>? = null

            withContext(Dispatchers.IO) {
                try {
                    response = service.getPopularMovie(apiKey)
                } catch (e: Exception) {
                    status.postValue(Status.Error(e.message!!))
                }
            }

            response?.let {
                if(it.isSuccessful) {
                    status.value = Status.Success
                    movies.value = it.body()?.results ?: listOf()
                } else {
                    status.value = Status.Error(it.message())
                }
            }
        }
    }*/
}