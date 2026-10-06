package com.example.anchor.notifications

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.anchor.R


object NotificationHelper {
    const val Channel_ID = "reminders"
    fun createChannel(context: Context){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                Channel_ID,
                "Reminders",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = context.getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }
    @SuppressLint("MissingPermission")
    fun show(context: Context){
        val notification = NotificationCompat.Builder(context, Channel_ID)
            .setSmallIcon(R.drawable.waterdropnotification)
            .setContentTitle("Have a sip of water")
            .setContentText("It's time to drink some water")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
        NotificationManagerCompat.from(context).notify(1, notification)
    }
}