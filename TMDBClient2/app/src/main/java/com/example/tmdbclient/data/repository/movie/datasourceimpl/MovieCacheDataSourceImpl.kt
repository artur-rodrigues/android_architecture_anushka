package com.example.tmdbclient.data.repository.movie.datasourceimpl

import com.example.tmdbclient.data.model.movie.Movie
import com.example.tmdbclient.data.repository.movie.datasource.MovieCacheDataSource

class MovieCacheDataSourceImpl() : MovieCacheDataSource {

    private val movieList = ArrayList<Movie>()

    override fun getMoviesFromCache(): List<Movie> {
        return movieList
    }

    override fun saveMoviesToCache(movies: List<Movie>) {
        movieList.clear()
        movieList.addAll(movies)
    }
}