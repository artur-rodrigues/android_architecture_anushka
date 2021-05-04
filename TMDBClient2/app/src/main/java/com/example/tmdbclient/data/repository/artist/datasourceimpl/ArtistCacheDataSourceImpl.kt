package com.example.tmdbclient.data.repository.artist.datasourceimpl

import com.example.tmdbclient.data.model.artist.Artist
import com.example.tmdbclient.data.repository.artist.datasource.ArtistCacheDataSource

class ArtistCacheDataSourceImpl() : ArtistCacheDataSource {

    private val artistList = ArrayList<Artist>()

    override fun getArtistsFromCache(): List<Artist> {
        return artistList
    }

    override fun saveArtistsToCache(artist: List<Artist>) {
        artistList.clear()
        artistList.addAll(artist)
    }
}