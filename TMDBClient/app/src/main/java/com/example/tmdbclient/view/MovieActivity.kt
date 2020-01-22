package com.example.tmdbclient.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.tmdbclient.R
import com.example.tmdbclient.databinding.ActivityMovieBinding
import com.example.tmdbclient.model.entity.Movie
import com.example.tmdbclient.util.toast

class MovieActivity : AppCompatActivity() {

    lateinit var binding: ActivityMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        intent.getParcelableExtra<Movie>(MOVIE)?.let {
            binding.movie = it
            supportActionBar?.title = it.title
        }
    }

    companion object {
        const val MOVIE = "movie"
    }
}
