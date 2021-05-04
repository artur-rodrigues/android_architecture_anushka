package com.example.tmdbclient.data.repository.tvshow.datasource

import com.example.tmdbclient.data.model.tvshow.TvShow

interface TvShowCacheDataSource {
    fun getTvShowFromCache(): List<TvShow>
    fun saveTvShowToCache(tvShows: List<TvShow>)
}