package com.example.tmdbclient.data.repository.movie.datasourceimpl

import com.example.tmdbclient.data.db.MovieDao
import com.example.tmdbclient.data.model.movie.Movie
import com.example.tmdbclient.data.repository.movie.datasource.MovieLocalDataSource
import com.example.tmdbclient.utils.runInIoAndForget

class MovieLocalDataSourceImpl(private val movieDao: MovieDao) : MovieLocalDataSource {

    override suspend fun getMoviesFromDB(): List<Movie> {
        return movieDao.getAllMovies()
    }

    override suspend fun saveMoviesToDB(movies: List<Movie>) {
        runInIoAndForget {
            movieDao.saveMovies(movies)
        }
    }

    override suspend fun clearAll() {
        runInIoAndForget {
            movieDao.deleteAllMovies()
        }
    }
}