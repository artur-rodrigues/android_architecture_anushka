package com.example.tmdbclient.presentation.artist

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tmdbclient.R
import com.example.tmdbclient.data.model.artist.Artist
import com.example.tmdbclient.data.model.tvshow.TvShow
import com.example.tmdbclient.databinding.ActivityGenericBinding
import com.example.tmdbclient.gone
import com.example.tmdbclient.presentation.commom.GenericAdapter
import com.example.tmdbclient.presentation.di.Injector
import com.example.tmdbclient.show
import com.example.tmdbclient.toast
import javax.inject.Inject

class ArtistActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ArtistViewModelFactory
    private lateinit var viewModel: ArtistViewModel
    private lateinit var binding: ActivityGenericBinding
    private lateinit var adapter: GenericAdapter<Artist>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_generic)

        (application as Injector).createArtistSubComponent()
            .inject(this)
        viewModel = ViewModelProvider(this, factory).get(ArtistViewModel::class.java)
        initArtistsList()
        displayArtists()
    }

    private fun initArtistsList() {
        adapter = GenericAdapter(object : GenericAdapter.FillAdapterInfo<Artist> {
            override fun getTitle(item: Artist) = item.name
            override fun getDescription(item: Artist) = item.popularity.toString()
            override fun getImageUrl(item: Artist) = item.profilePath
        })
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@ArtistActivity)
            adapter = this@ArtistActivity.adapter
        }
    }

    private fun displayArtists() {
        binding.progressBar.show()
        viewModel.getArtists().observe(this) {
            it?.let {artists ->
                adapter.setList(artists as ArrayList<Artist>)
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
        viewModel.updateArtistList().observe(this) {
            it?.let { artists ->
                adapter.setList(artists as ArrayList<Artist>)
            } ?: toast("No data available!")

            binding.progressBar.gone()
        }
    }
}