package com.example.tmdbclient.data.repository.movie.datasource

import com.example.tmdbclient.data.model.movie.Movie

interface MovieCacheDataSource {
    fun getMoviesFromCache(): List<Movie>
    fun saveMoviesToCache(movies: List<Movie>)
}