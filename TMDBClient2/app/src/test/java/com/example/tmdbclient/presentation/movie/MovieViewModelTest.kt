package com.example.tmdbclient.presentation.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.tmdbclient.data.model.movie.Movie
import com.example.tmdbclient.data.repository.movie.FakeMovieRepository
import com.example.tmdbclient.domain.usecase.GetMoviesUseCase
import com.example.tmdbclient.domain.usecase.UpdateMoviesUseCase
import com.example.tmdbclient.getOrAwaitValue
import com.google.common.truth.Truth
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var movieViewModel: MovieViewModel

    @Before
    fun setUp() {
        val fakeMovieRepository = FakeMovieRepository()
        val getMoviesUseCase = GetMoviesUseCase(fakeMovieRepository)
        val updateMoviesUseCase = UpdateMoviesUseCase(fakeMovieRepository)
        movieViewModel = MovieViewModel(getMoviesUseCase, updateMoviesUseCase)

    }

    @After
    fun tearDown() {
    }

    @Test
    fun getMovies_returnCurrentList() {
        val movies = mutableListOf(
            Movie(1, "overview1", "posterPath1", "releaseDate1", "title1"),
            Movie(2, "overview2", "posterPath2", "releaseDate2", "title2")
        )

        val moviesList = movieViewModel.getMovies().getOrAwaitValue()
        Truth.assertThat(moviesList).isEqualTo(movies)
    }

    @Test
    fun updateMovieList_returnUpdateList() {
        val movies = mutableListOf(
            Movie(3, "overview3", "posterPath3", "releaseDate3", "title3"),
            Movie(4, "overview4", "posterPath4", "releaseDate4", "title4")
        )

        val moviesList = movieViewModel.updateMovieList().getOrAwaitValue()
        Truth.assertThat(moviesList).isEqualTo(movies)
    }
}