package com.example.notificationdemo

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import androidx.databinding.DataBindingUtil
import com.example.notificationdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val channelId = "com.example.notificationdemo.channel1"
    private var notificationManager: NotificationManager? = null
    private val KEY_REPLY = "key_reply"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.notifyButton.setOnClickListener {
            displayNotification()
        }

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
        createNotificationChannel(channelId, "DemoChannel", "This is a demo")
    }

    private fun displayNotification() {
        val notificationId = 1

        val pendingIntent = getPendingIntent(SecondActivity::class.java)
        val remoteInput = RemoteInput.Builder(KEY_REPLY)
            .setLabel("Insert your name here")
            .build()
        val replayAction = NotificationCompat.Action.Builder(0, "REPLY", pendingIntent)
            .addRemoteInput(remoteInput).build()

        val pendingIntent2 = getPendingIntent(DetailsActivity::class.java)
        val action2 = NotificationCompat.Action.Builder(0, "Details", pendingIntent2).build()

        val pendingIntent3 = getPendingIntent(SettingsActivity::class.java)
        val action3 = NotificationCompat.Action.Builder(0, "Settings", pendingIntent3).build()

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Demo Title")
            .setContentText("This is a demo notification")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
//            .setContentIntent(pendingIntent)
            .addAction(replayAction)
            .addAction(action2)
            .addAction(action3)
            .build()
        notificationManager?.notify(notificationId, notification)
    }

    private fun getPendingIntent(clazz: Class<out AppCompatActivity>): PendingIntent {
        return PendingIntent.getActivity(
            this, 0,
            Intent(this, clazz),
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    private fun createNotificationChannel(id: String, name: String, channelDescription: String) {
        /*Verificação necessária se o sdk minimo for menor que o 26
        * abaixo do 26 não é necessário criar um canal de notificação
        * if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {}*/
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(id, name, importance).apply {
            description = channelDescription
        }
        notificationManager?.createNotificationChannel(channel)
    }
}