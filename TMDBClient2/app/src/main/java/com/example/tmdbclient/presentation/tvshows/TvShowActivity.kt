package com.example.tmdbclient.presentation.tvshows

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tmdbclient.R
import com.example.tmdbclient.data.model.tvshow.TvShow
import com.example.tmdbclient.databinding.ActivityGenericBinding
import com.example.tmdbclient.gone
import com.example.tmdbclient.presentation.commom.GenericAdapter
import com.example.tmdbclient.presentation.di.Injector
import com.example.tmdbclient.show
import com.example.tmdbclient.toast
import javax.inject.Inject

class TvShowActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: TvShowViewModelFactory
    private lateinit var viewModel: TvShowViewModel
    private lateinit var binding: ActivityGenericBinding
    private lateinit var adapter: GenericAdapter<TvShow>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_generic)

        (application as Injector).createTvShowSubComponent()
            .inject(this)
        viewModel = ViewModelProvider(this, factory).get(TvShowViewModel::class.java)
        initTvShowList()
        displayTvShow()
    }

    private fun initTvShowList() {
        adapter = GenericAdapter(object : GenericAdapter.FillAdapterInfo<TvShow> {
            override fun getTitle(item: TvShow) = item.name
            override fun getDescription(item: TvShow) = item.overview
            override fun getImageUrl(item: TvShow) = item.posterPath
        })
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@TvShowActivity)
            adapter = this@TvShowActivity.adapter
        }
    }

    private fun displayTvShow() {
        binding.progressBar.show()
        viewModel.getTvShows().observe(this) {
            it?.let { tvShow ->
                adapter.setList(tvShow as ArrayList<TvShow>)
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
                updateTvShows()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun updateTvShows() {
        binding.progressBar.show()
        viewModel.updateTvShowList().observe(this) {
            it?.let { tvShow ->
                adapter.setList(tvShow as ArrayList<TvShow>)
            } ?: toast("No data available!")

            binding.progressBar.gone()
        }
    }
}