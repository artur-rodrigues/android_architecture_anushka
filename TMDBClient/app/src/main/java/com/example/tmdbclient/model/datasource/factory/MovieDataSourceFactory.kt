package com.example.tmdbclient.model.datasource.factory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.tmdbclient.model.datasource.MovieDataSource
import com.example.tmdbclient.model.entity.Movie
import com.example.tmdbclient.model.service.MovieDataService

class MovieDataSourceFactory(
    private val movieDataService: MovieDataService
): DataSource.Factory<Long, Movie>() {

    private lateinit var movieDataSource: MovieDataSource

    private val dataSourceLiveData = MutableLiveData<MovieDataSource>()
    fun getDataSource(): LiveData<MovieDataSource> = dataSourceLiveData

    override fun create(): DataSource<Long, Movie> {
        movieDataSource = MovieDataSource(movieDataService)
        dataSourceLiveData.postValue(movieDataSource)
        return movieDataSource
    }
}