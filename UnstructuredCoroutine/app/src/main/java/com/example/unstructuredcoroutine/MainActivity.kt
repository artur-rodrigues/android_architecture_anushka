package com.example.unstructuredcoroutine

import android.os.Bundle
import android.util.Log.i
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnCount.setOnClickListener {
            tvCount.text = count++.toString()
        }

        btnDownloadUserData.setOnClickListener {
            CoroutineScope(Main).launch {
//                tvUserMessage.text = UserDataManager.getTotalUserCount().toString()
                tvUserMessage.text = UserDataManager2.getTotalUserCount().toString()
            }
        }
    }

    private fun downloadUserData() {
        for (i in 1..200000) {
            i("MyTag", "Downloading user $i in ${Thread.currentThread().name}")
        }
    }
}