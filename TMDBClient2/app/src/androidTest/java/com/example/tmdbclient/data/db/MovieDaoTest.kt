package com.example.tmdbclient.data.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.tmdbclient.data.model.movie.Movie
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var dao: MovieDao
    private lateinit var dataBase: TMDBDatabase

    private val movies = listOf(
        Movie(1, "overview1", "posterPath1", "releaseDate1", "title1"),
        Movie(2, "overview2", "posterPath2", "releaseDate2", "title2"),
        Movie(3, "overview3", "posterPath3", "releaseDate3", "title3")
    )

    @Before
    fun setUp() {
        dataBase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TMDBDatabase::class.java
        ).build()

        dao = dataBase.movieDao()
    }

    @After
    fun tearDown() {
        dataBase.close()
    }

    @Test
    fun saveMovieTest() = runBlocking {
        dao.saveMovies(movies)

        val allMovies = dao.getAllMovies()
        Truth.assertThat(allMovies).isEqualTo(movies)
    }

    @Test
    fun deleteMovieTest() = runBlocking {
        dao.saveMovies(movies)
        dao.deleteAllMovies()
        val allMovies = dao.getAllMovies()
        Truth.assertThat(allMovies).isEmpty()
    }
}