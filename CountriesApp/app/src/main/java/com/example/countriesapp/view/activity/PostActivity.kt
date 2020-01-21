package com.example.countriesapp.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.countriesapp.R
import com.example.countriesapp.databinding.ActivityPostBinding
import com.example.countriesapp.model.service.RetrofitInstance
import com.example.countriesapp.viewmodel.PostActivityViewModel
import com.example.countriesapp.viewmodel.factory.ViewModelFactory

class PostActivity : AppCompatActivity() {

    lateinit var binding: ActivityPostBinding
    lateinit var viewModel: PostActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_post)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_post)
        viewModel = ViewModelProvider(this,
            ViewModelFactory(RetrofitInstance.getCountryService()))
            .get(PostActivityViewModel::class.java)
    }
}
