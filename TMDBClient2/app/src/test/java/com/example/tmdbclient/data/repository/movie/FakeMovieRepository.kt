package com.example.tmdbclient.data.repository.movie

import com.example.tmdbclient.data.model.movie.Movie
import com.example.tmdbclient.domain.repository.MovieRepository

class FakeMovieRepository : MovieRepository {

    private val movies = mutableListOf<Movie>()

    init {
        movies.add(Movie(1, "overview1", "posterPath1", "releaseDate1", "title1"))
        movies.add(Movie(2, "overview2", "posterPath2", "releaseDate2", "title2"))
    }

    override suspend fun getMovies(): List<Movie>? {
        return movies
    }

    override suspend fun updateMovies(): List<Movie> {
        movies.clear()
        movies.add(Movie(3, "overview3", "posterPath3", "releaseDate3", "title3"))
        movies.add(Movie(4, "overview4", "posterPath4", "releaseDate4", "title4"))
        return movies
    }
}