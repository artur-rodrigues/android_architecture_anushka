package com.example.tmdbclient.domain.usecase

import com.example.tmdbclient.data.model.movie.Movie
import com.example.tmdbclient.domain.repository.MovieRepository

class UpdateMoviesUseCase(private val repository: MovieRepository) {

    suspend fun execute() = repository.updateMovies()
}