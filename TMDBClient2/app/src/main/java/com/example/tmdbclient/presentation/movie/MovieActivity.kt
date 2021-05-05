package com.example.tmdbclient.presentation.movie

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tmdbclient.R
import com.example.tmdbclient.data.model.movie.Movie
import com.example.tmdbclient.databinding.ActivityGenericBinding
import com.example.tmdbclient.gone
import com.example.tmdbclient.presentation.commom.GenericAdapter
import com.example.tmdbclient.presentation.di.Injector
import com.example.tmdbclient.show
import com.example.tmdbclient.toast
import javax.inject.Inject

class MovieActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: MovieViewModelFactory
    private lateinit var viewModel: MovieViewModel
    private lateinit var binding: ActivityGenericBinding
    private lateinit var adapter: GenericAdapter<Movie>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_generic)

        (application as Injector).createMovieSubComponent()
            .inject(this)
        viewModel = ViewModelProvider(this, factory).get(MovieViewModel::class.java)
        initMoviesList()
        displayMovies()
    }

    private fun initMoviesList() {
        adapter = GenericAdapter(object : GenericAdapter.FillAdapterInfo<Movie> {
            override fun getTitle(item: Movie) = item.title
            override fun getDescription(item: Movie) = item.overview
            override fun getImageUrl(item: Movie) = item.posterPath
        })
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MovieActivity)
            adapter = this@MovieActivity.adapter
        }
    }

    private fun displayMovies() {
        binding.progressBar.show()
        viewModel.getMovies().observe(this) {
            it?.let { movies ->
                adapter.setList(movies as ArrayList<Movie>)
            } ?: toast("No data available!")

            binding.progressBar.gone()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.update, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_update -> {
                updateMovies()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun updateMovies() {
        binding.progressBar.show()
        viewModel.updateMovieList().observe(this) {
            it?.let { movies ->
                adapter.setList(movies as ArrayList<Movie>)
            } ?: toast("No data available!")

            binding.progressBar.gone()
        }
    }
}