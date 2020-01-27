package com.example.activitystatesdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.activitystatesdemo.databinding.ActivitySecondBinding
import com.example.activitystatesdemo.utils.log

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding
    private val activityName = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_second)
        log("$activityName onCreate()")
    }

    override fun onStart() {
        super.onStart()
        log("$activityName onStart()")
    }

    override fun onResume() {
        super.onResume()
        log("$activityName onResume()")
    }

    override fun onPostResume() {
        super.onPostResume()
        log("$activityName onPostResume()")
    }

    override fun onPause() {
        super.onPause()
        log("$activityName onPause()")
    }

    override fun onStop() {
        super.onStop()
        log("$activityName onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        log("$activityName onDestroy()")
    }

    override fun onRestart() {
        super.onRestart()
        log("$activityName onRestart()")
    }
}
