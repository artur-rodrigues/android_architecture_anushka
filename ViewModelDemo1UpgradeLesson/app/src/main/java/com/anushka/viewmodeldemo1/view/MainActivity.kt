package com.anushka.viewmodeldemo1.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.anushka.viewmodeldemo1.R
import com.anushka.viewmodeldemo1.databinding.ActivityMainBinding
import com.anushka.viewmodeldemo1.viewmodel.MainActivityViewModel
import com.anushka.viewmodeldemo1.viewmodel.factory.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        viewModel = ViewModelProvider(this, MainViewModelFactory(5)).get(MainActivityViewModel::class.java)

        setUpView()
        setUpObservers()
    }

    private fun setUpObservers() {
        viewModel.getSum().observe(this, Observer {
            binding.resultSum.text = it.toString()
        })
    }

    private fun setUpView() {
        binding.button.setOnClickListener {
            viewModel.makeSum(binding.inputNumber.text.toString().toInt())
        }
    }
}
