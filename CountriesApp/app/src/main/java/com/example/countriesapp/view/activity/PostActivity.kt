package com.example.countriesapp.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.countriesapp.R
import com.example.countriesapp.databinding.ActivityPostBinding
import com.example.countriesapp.model.entity.Result
import com.example.countriesapp.model.entity.User
import com.example.countriesapp.model.service.RetrofitInstance
import com.example.countriesapp.util.clear
import com.example.countriesapp.util.texto
import com.example.countriesapp.util.toast
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

        setUpBinding()
        setUpViewModel()
    }

    private fun setUpBinding() {
        binding.btnSubmit.setOnClickListener {
            with(binding) {
                val user = User(
                    title = etEmail.texto(),
                    body = etPassword.texto()
                )
                viewModel.postData(user)
            }

        }
    }

    private fun setUpViewModel() {
        viewModel.status.observe(this, Observer {
            when(it) {
                Result.Loading -> {
                    // Todo Show Progress

                }

                Result.Success -> {
                    binding.run {
                        etEmail.clear()
                        etPassword.clear()
                    }
                }

                is Result.Error -> {
                    // Todo Hide progress
                    toast(it.error)
                }
            }
        })

        viewModel.user.observe(this, Observer {
//            binding.tvResult.text = "Id is: ${it.id}"
            binding.tvResult.text = getString(R.string.id_is, it.id)
        })
    }
}
