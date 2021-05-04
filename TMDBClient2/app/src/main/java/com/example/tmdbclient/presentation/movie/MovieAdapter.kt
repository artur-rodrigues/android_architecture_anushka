package com.example.tmdbclient.presentation.movie

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdbclient.BuildConfig
import com.example.tmdbclient.data.model.movie.Movie
import com.example.tmdbclient.databinding.ListItemBinding
import com.example.tmdbclient.loadImage

class MovieAdapter : RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}

class MyViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun binding(movie: Movie) {
        binding.run {
            titleTextView.text = movie.title
            descriptionTextView.text = movie.overview
            imageView.loadImage(BuildConfig.BASE_IMAGE_URL + movie.posterPath)
        }
    }
}