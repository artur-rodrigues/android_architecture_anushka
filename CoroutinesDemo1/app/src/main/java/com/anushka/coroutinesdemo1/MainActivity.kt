package com.anushka.coroutinesdemo1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.anushka.coroutinesdemo1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var count = 0

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setUpBinding()
    }

    private fun setUpBinding() {
        binding.apply {
            btnCount.setOnClickListener {
                tvCount.text = count++.toString()
            }

            btnDownloadUserData.setOnClickListener {
                downloadUserData()
            }
        }
    }

    private fun downloadUserData() {
        for (i in 1..200000) {
            Log.i("MyTag", "Downloading user $i in ${Thread.currentThread().name}")
        }
    }
}
