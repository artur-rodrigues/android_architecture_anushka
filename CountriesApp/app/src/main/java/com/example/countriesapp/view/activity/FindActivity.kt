package com.example.countriesapp.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.countriesapp.R
import com.example.countriesapp.databinding.ActivityFindBinding
import com.example.countriesapp.model.entity.Result
import com.example.countriesapp.model.service.RetrofitInstance
import com.example.countriesapp.util.toInt
import com.example.countriesapp.util.toast
import com.example.countriesapp.viewmodel.FindActivityViewModel
import com.example.countriesapp.viewmodel.MainActivityViewModel
import com.example.countriesapp.viewmodel.factory.ViewModelFactory

class FindActivity : AppCompatActivity() {

    lateinit var binding: ActivityFindBinding
    lateinit var viewModel: FindActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_find)
        viewModel = ViewModelProvider(this,
            ViewModelFactory(RetrofitInstance.getCountryService()))
            .get(FindActivityViewModel::class.java)

        setUpObservers()
        setUpBinding()
    }

    private fun setUpBinding() {
        binding.btnSubmit.setOnClickListener {
            if(TextUtils.isEmpty(binding.etCode.text)) {
                toast("Parâmetro obrigatório")
            } else {
                viewModel.fetchCountry(binding.etCode.toInt())
            }
        }

        binding.btnClear.setOnClickListener {
            binding.tvCountryName.text = null
            binding.etCode.text = null
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

        viewModel.country.observe(this, Observer {
            binding.tvCountryName.text = it.title
        })
    }
}
