package com.example.tmdbclient.view

import androidx.recyclerview.widget.DiffUtil
import com.example.tmdbclient.model.entity.Movie

class MovieDiffCallBack: DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
//        return oldItem == newItem
        return true
    }
}