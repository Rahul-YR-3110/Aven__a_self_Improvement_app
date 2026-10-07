package com.example.anchor.ui.pages.water

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.anchor.waterReminderworker
import java.util.concurrent.TimeUnit

object ReminderScheduler {
    private const val WORK_NAME = "WaterReminderWork"

    fun start(context: Context, intervalMinutes: Int) {
        val minutes = intervalMinutes.coerceAtLeast(15).toLong()
        val workRequest = PeriodicWorkRequestBuilder<waterReminderworker>(
            minutes, TimeUnit.MINUTES
        ).build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            WORK_NAME,
            ExistingPeriodicWorkPolicy.UPDATE,
            workRequest
        )
    }
    fun stop(context: Context) {
        WorkManager.getInstance(context).cancelUniqueWork(WORK_NAME)
    }
}