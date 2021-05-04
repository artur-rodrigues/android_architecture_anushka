package com.example.tmdbclient.data.repository.artist.datasource

import com.example.tmdbclient.data.model.artist.Artist

interface ArtistCacheDataSource {
    fun getArtistsFromCache(): List<Artist>
    fun saveArtistsToCache(artist: List<Artist>)
}