package com.example.roomdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdemo.adapter.MyRecyclerViewAdapter
import com.example.roomdemo.databinding.ActivityMainBinding
import com.example.roomdemo.db.Subscriber
import com.example.roomdemo.db.SubscriberDataBase
import com.example.roomdemo.db.SubscriberRepository
import com.example.roomdemo.viewmodel.SubscriberViewModel
import com.example.roomdemo.viewmodel.SubscriberViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: SubscriberViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val dao = SubscriberDataBase.getInstance(this).subscriberDAO
        val repository = SubscriberRepository(dao)
        val factory = SubscriberViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(SubscriberViewModel::class.java)
        binding.myViewModel = viewModel
        binding.lifecycleOwner = this

        initRecyclerView()

        viewModel.message.observe(this) {
            it.getContentIfNotHandle()?.let { msg ->
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initRecyclerView() {
        binding.subscriberRecyclerview.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            this.adapter = MyRecyclerViewAdapter {
                itemClick(it)
            }
        }
        displaySubscriberList()
    }

    private fun displaySubscriberList() {
        viewModel.subscribers.observe(this) {
            binding.subscriberRecyclerview.adapter.run {
                (this as MyRecyclerViewAdapter).setSubscribers(it)
            }
        }
    }

    private fun itemClick(subscriber: Subscriber) {
        viewModel.initUpdateAndDelete(subscriber)
    }
}