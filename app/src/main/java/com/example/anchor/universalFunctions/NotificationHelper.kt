package com.example.anchor.universalFunctions

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
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
    fun waterRminderNotification(context: Context){
        createChannel(context)
        val waterMessages = listOf(
            "Have a sip of water" to "It's time to drink some water",
            "Hydration check 💧" to "Your body will thank you for a glass of water",
            "Water break!" to "Pause for a moment and take a few sips",
            "Stay hydrated" to "A small glass now keeps the tiredness away",
            "Quick reminder" to "Your water bottle is waiting for you"
        )
        val (title, message) = waterMessages.random()
        val notification = NotificationCompat.Builder(context, Channel_ID)
            .setSmallIcon(R.drawable.waterdropnotification)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
        NotificationManagerCompat.from(context).notify(1, notification)
    }
    fun hasPermission(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }
}