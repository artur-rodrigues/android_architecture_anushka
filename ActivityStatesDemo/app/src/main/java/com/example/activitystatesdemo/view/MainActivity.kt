package com.example.activitystatesdemo.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.activitystatesdemo.R
import com.example.activitystatesdemo.component.DemoAppComponent
import com.example.activitystatesdemo.databinding.ActivityMainBinding
import com.example.activitystatesdemo.utils.log

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
//    private val activityName = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        setSupportActionBar(binding.toolbar)

        binding.fab.setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }

        lifecycle.addObserver(DemoAppComponent(this::class.java.simpleName))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
