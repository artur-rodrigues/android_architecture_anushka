package com.example.tmdbclient.data.repository.tvshow

import com.example.tmdbclient.data.model.tvshow.TvShow
import com.example.tmdbclient.data.repository.tvshow.datasource.TvShowCacheDataSource
import com.example.tmdbclient.data.repository.tvshow.datasource.TvShowLocalDataSource
import com.example.tmdbclient.data.repository.tvshow.datasource.TvShowRemoteDataSource
import com.example.tmdbclient.domain.repository.TvShowRepository
import com.example.tmdbclient.utils.log

class TvShowRepositoryImpl(
    private val remoteDataSource: TvShowRemoteDataSource,
    private val localDataSource: TvShowLocalDataSource,
    private val cacheDataSource: TvShowCacheDataSource
) : TvShowRepository {

    override suspend fun getTvShows(): List<TvShow> {
        return getMoviesFromCache()
    }

    override suspend fun updateTvShows(): List<TvShow> {
        val tvShows = getTvShowsFromApi()
        localDataSource.clearAll()
        localDataSource.saveTvShowToDB(tvShows)
        cacheDataSource.saveTvShowToCache(tvShows)
        return tvShows
    }

    private suspend fun getTvShowsFromApi(): List<TvShow> {
        lateinit var tvShows: List<TvShow>

        try {
            val response = remoteDataSource.getTvShows()
            response.body()?.let {
                tvShows = it.tvShows
            }
        } catch (ex: Exception) {
            log(ex.message.toString())
        }

        return tvShows
    }

    private suspend fun getTvShowsFromDB(): List<TvShow> {
        lateinit var tvShows: List<TvShow>

        try {
            tvShows = localDataSource.getTvShowFromDB()
        } catch (ex: Exception) {
            log(ex.message.toString())
        }

        if (tvShows.isEmpty()) {
            tvShows = getTvShowsFromApi()
            localDataSource.saveTvShowToDB(tvShows)
        }

        return tvShows
    }

    private suspend fun getMoviesFromCache(): List<TvShow> {
        lateinit var tvShows: List<TvShow>

        try {
            tvShows = cacheDataSource.getTvShowFromCache()
        } catch (ex: Exception) {
            log(ex.message.toString())
        }

        if (tvShows.isEmpty()) {
            tvShows = getTvShowsFromDB()
            cacheDataSource.saveTvShowToCache(tvShows)
        }

        return tvShows
    }
}