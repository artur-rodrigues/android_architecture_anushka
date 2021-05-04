package com.example.tmdbclient.data.repository.tvshow.datasourceimpl

import com.example.tmdbclient.data.model.tvshow.TvShow
import com.example.tmdbclient.data.repository.tvshow.datasource.TvShowCacheDataSource

class TvShowCacheDataSourceImpl() : TvShowCacheDataSource {

    private val tvShowList = ArrayList<TvShow>()

    override fun getTvShowFromCache(): List<TvShow> {
        return tvShowList
    }

    override fun saveTvShowToCache(tvShows: List<TvShow>) {
        tvShowList.clear()
        tvShowList.addAll(tvShows)
    }
}