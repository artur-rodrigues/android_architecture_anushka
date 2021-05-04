package com.example.tmdbclient.domain.usecase

import com.example.tmdbclient.domain.repository.TvShowRepository

class UpdateTvShowsUseCase(private val repository: TvShowRepository) {

    suspend fun execute() = repository.updateTvShows()
}