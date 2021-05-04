package com.example.tmdbclient.data.repository.movie

import com.example.tmdbclient.data.model.movie.Movie
import com.example.tmdbclient.data.repository.movie.datasource.MovieCacheDataSource
import com.example.tmdbclient.data.repository.movie.datasource.MovieLocalDataSource
import com.example.tmdbclient.data.repository.movie.datasource.MovieRemoteDataSource
import com.example.tmdbclient.domain.repository.MovieRepository
import com.example.tmdbclient.utils.log

class MovieRepositoryImpl(
    private val remoteDataSource: MovieRemoteDataSource,
    private val localDataSource: MovieLocalDataSource,
    private val cacheDataSource: MovieCacheDataSource
) : MovieRepository {

    override suspend fun getMovies(): List<Movie> {
        return getMoviesFromCache()
    }

    override suspend fun updateMovies(): List<Movie> {
        val movies = getMoviesFromApi()
        localDataSource.clearAll()
        localDataSource.saveMoviesToDB(movies)
        cacheDataSource.saveMoviesToCache(movies)
        return movies
    }

    private suspend fun getMoviesFromApi(): List<Movie> {
        lateinit var movies: List<Movie>

        try {
            val response = remoteDataSource.getMovies()
            response.body()?.let {
                movies = it.movies
            }
        } catch (ex: Exception) {
            log(ex.message.toString())
        }

        return movies
    }

    private suspend fun getMoviesFromDB(): List<Movie> {
        lateinit var movies: List<Movie>

        try {
            movies = localDataSource.getMoviesFromDB()
        } catch (ex: Exception) {
            log(ex.message.toString())
        }

        if (movies.isEmpty()) {
            movies = getMoviesFromApi()
            localDataSource.saveMoviesToDB(movies)
        }

        return movies
    }

    private suspend fun getMoviesFromCache(): List<Movie> {
        lateinit var movies: List<Movie>

        try {
            movies = cacheDataSource.getMoviesFromCache()
        } catch (ex: Exception) {
            log(ex.message.toString())
        }

        if (movies.isEmpty()) {
            movies = getMoviesFromDB()
            cacheDataSource.saveMoviesToCache(movies)
        }

        return movies
    }
}