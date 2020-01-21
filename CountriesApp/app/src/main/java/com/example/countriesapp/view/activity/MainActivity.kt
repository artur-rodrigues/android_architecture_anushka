package com.example.countriesapp.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.countriesapp.R
import com.example.countriesapp.databinding.ActivityMainBinding
import com.example.countriesapp.model.entity.Country
import com.example.countriesapp.model.entity.Result
import com.example.countriesapp.model.service.RetrofitInstance
import com.example.countriesapp.util.toast
import com.example.countriesapp.view.adapter.CountryAdapter
import com.example.countriesapp.viewmodel.MainActivityViewModel
import com.example.countriesapp.viewmodel.factory.ViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var countries: ArrayList<Country>
    lateinit var viewModel: MainActivityViewModel
    private val adapter = CountryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this,
            ViewModelFactory(RetrofitInstance.getCountryService()))
            .get(MainActivityViewModel::class.java)

        binding.fab.setOnClickListener {
            startActivity(Intent(this, FindActivity::class.java))
        }

        setUpRecyclerView()
        setUpObservers()

        viewModel.fetchCountries()
    }

    private fun setUpRecyclerView() {
        binding.rvCountryList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity.applicationContext)
            setHasFixedSize(true)
            adapter = this@MainActivity.adapter
        }
    }

    private fun setUpObservers() {
        viewModel.status.observe(this, Observer {
            when(it) {
                Result.Loading -> {
                    // Todo Show Progress

                }

                Result.Success -> {
                    // Todo Hide Progress

                }

                is Result.Error -> {
                    // Todo Hide progress
                    toast(it.error)
                }
            }
        })

        viewModel.countries.observe(this, Observer {
            this.countries = it
            adapter.countries = it
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.findCountry -> {
                startActivity(Intent(this, FindActivity::class.java))
                return true
            }

            R.id.doPost -> {
                startActivity(Intent(this, PostActivity::class.java))
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
