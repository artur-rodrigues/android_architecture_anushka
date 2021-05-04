package com.example.tmdbclient.data.repository.artist

import com.example.tmdbclient.data.model.artist.Artist
import com.example.tmdbclient.data.model.movie.Movie
import com.example.tmdbclient.data.repository.artist.datasource.ArtistCacheDataSource
import com.example.tmdbclient.data.repository.artist.datasource.ArtistLocalDataSource
import com.example.tmdbclient.data.repository.artist.datasource.ArtistRemoteDataSource
import com.example.tmdbclient.domain.repository.ArtistRepository
import com.example.tmdbclient.utils.log

class ArtistRepositoryImpl(
    private val remoteDataSource: ArtistRemoteDataSource,
    private val localDataSource: ArtistLocalDataSource,
    private val cacheDataSource: ArtistCacheDataSource
) : ArtistRepository {

    override suspend fun getArtists(): List<Artist> {
        return getArtistsFromCache()
    }

    override suspend fun updateArtists(): List<Artist> {
        val movies = getArtistsFromApi()
        localDataSource.clearAll()
        localDataSource.saveArtistsToDB(movies)
        cacheDataSource.saveArtistsToCache(movies)
        return movies
    }

    private suspend fun getArtistsFromApi(): List<Artist> {
        lateinit var artist: List<Artist>

        try {
            val response = remoteDataSource.getArtists()
            response.body()?.let {
                artist = it.artists
            }
        } catch (ex: Exception) {
            log(ex.message.toString())
        }

        return artist
    }

    private suspend fun getArtistsFromDB(): List<Artist> {
        lateinit var artist: List<Artist>

        try {
            artist = localDataSource.getArtistsFromDB()
        } catch (ex: Exception) {
            log(ex.message.toString())
        }

        if (artist.isEmpty()) {
            artist = getArtistsFromApi()
            localDataSource.saveArtistsToDB(artist)
        }

        return artist
    }

    private suspend fun getArtistsFromCache(): List<Artist> {
        lateinit var artists: List<Artist>

        try {
            artists = cacheDataSource.getArtistsFromCache()
        } catch (ex: Exception) {
            log(ex.message.toString())
        }

        if (artists.isEmpty()) {
            artists = getArtistsFromDB()
            cacheDataSource.saveArtistsToCache(artists)
        }

        return artists
    }
}