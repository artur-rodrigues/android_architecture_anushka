package com.example.tmdbclient.view

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tmdbclient.R
import com.example.tmdbclient.databinding.ActivityMainBinding
import com.example.tmdbclient.model.entity.Movie
import com.example.tmdbclient.model.entity.Status
import com.example.tmdbclient.model.service.RetrofitInstance
import com.example.tmdbclient.util.toast
import com.example.tmdbclient.view.adapter.MovieAdapter
import com.example.tmdbclient.view.factory.ViewModelFactory
import com.example.tmdbclient.viewmodel.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainActivityViewModel
    lateinit var movies: PagedList<Movie>
    private val adapter = MovieAdapter {
        val intent = Intent(this, MovieActivity::class.java)
        intent.putExtra(MovieActivity.MOVIE, it)
        startActivity(intent)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this,
            ViewModelFactory(RetrofitInstance.getMovieService()))
            .get(MainActivityViewModel::class.java)
        supportActionBar?.title = "TMDB PopularMovies Today"

        setUpBinding()
        setUpViewModel()

//        viewModel.fetchMovies(getString(R.string.api_key))
    }

    private fun setUpViewModel() {
        viewModel.getStatus().observe(this, Observer {
            when(it) {
                Status.Loading -> {
                    // Todo Show Progress

                }

                Status.Success -> {
                    binding.swipe.isRefreshing = false
                }

                is Status.Error -> {
                    // Todo Hide progress
                    binding.swipe.isRefreshing = false
                    toast(it.error)
                }
            }
        })

        /*viewModel.getMovie().observe(this, Observer {
            this.movies = it
            this.adapter.movies = it
        })*/

        viewModel.moviesPagedList.observe(this, Observer {
            movies = it
            adapter.submitList(it)
        })
    }

    private fun setUpBinding() {
        binding.rvMovies.apply {
            val span = if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                2
            } else {
                4
            }

            setHasFixedSize(true)
            layoutManager = GridLayoutManager(this@MainActivity, span)
            itemAnimator = DefaultItemAnimator()
            adapter = this@MainActivity.adapter
        }

        binding.swipe.setColorSchemeResources(R.color.colorPrimary)
        binding.swipe.setOnRefreshListener {
//            viewModel.fetchMovies(getString(R.string.api_key))
        }
    }
}
