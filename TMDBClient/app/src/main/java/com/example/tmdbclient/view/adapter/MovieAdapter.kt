package com.example.tmdbclient.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdbclient.R
import com.example.tmdbclient.databinding.MovieListItemBinding
import com.example.tmdbclient.model.entity.Movie
import com.example.tmdbclient.view.MovieDiffCallBack

typealias Click = (Movie) -> Unit

class MovieAdapter(val click: Click = {}):
    PagedListAdapter<Movie, MovieAdapter.MovieHolder>(MovieDiffCallBack()) {

    /*var movies = listOf<Movie>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val binding: MovieListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.movie_list_item,
            parent,
            false
        )

        return MovieHolder(binding)
    }

    /*override fun getItemCount(): Int {
        return movies.size
    }*/

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val movie = getItem(position)
        holder.binding.movie = movie
        holder.binding.root.setOnClickListener {
            click(movie!!)
        }
    }

    class MovieHolder(val binding: MovieListItemBinding):
        RecyclerView.ViewHolder(binding.root)
}