package com.example.tmdbclient.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.tmdbclient.R
import com.example.tmdbclient.databinding.ActivityHomeBinding
import com.example.tmdbclient.presentation.artist.ArtistActivity
import com.example.tmdbclient.presentation.movie.MovieActivity
import com.example.tmdbclient.presentation.tvshows.TvShowActivity
import com.example.tmdbclient.utils.goToActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        binding.movieButton.setOnClickListener {
            goToActivity(MovieActivity::class.java)
        }
        binding.tvButton.setOnClickListener {
            goToActivity(TvShowActivity::class.java)
        }
        binding.artistsButton.setOnClickListener {
            goToActivity(ArtistActivity::class.java)
        }
    }
}