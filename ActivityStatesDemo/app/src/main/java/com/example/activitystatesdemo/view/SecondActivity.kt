package com.example.activitystatesdemo.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.activitystatesdemo.R
import com.example.activitystatesdemo.component.DemoAppComponent
import com.example.activitystatesdemo.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_second)
        lifecycle.addObserver(DemoAppComponent(this::class.java.simpleName))
    }
}
