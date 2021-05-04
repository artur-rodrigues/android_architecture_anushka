package com.example.notificationdemo

import android.app.NotificationManager
import android.app.RemoteInput
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.databinding.DataBindingUtil
import com.example.notificationdemo.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding
    private val KEY_REPLY = "key_reply"
    private val channelId = "com.example.notificationdemo.channel1"
    private val notificationId = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_second)
        receiveInput()
    }

    private fun receiveInput() {
        val resultsFromIntent = RemoteInput.getResultsFromIntent(intent)
        resultsFromIntent?.let {
            reply(it.getCharSequence(KEY_REPLY).toString())
        }
    }

    private fun reply(reply: String) {
        binding.textView2.text = reply

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentText("Your reply received")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .build()

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
        notificationManager?.notify(notificationId, notification)
    }
}