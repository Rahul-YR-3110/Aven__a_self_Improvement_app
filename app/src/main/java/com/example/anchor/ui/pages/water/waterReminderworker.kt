package com.example.anchor

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.anchor.notifications.NotificationHelper
import java.util.Calendar

class waterReminderworker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val startHour = ReminderSettings.startHour(applicationContext)
        val endHour = ReminderSettings.endHour(applicationContext)

        val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

        val insideRange = currentHour in startHour until endHour
        val allowed = NotificationHelper.hasPermission(applicationContext)

        if (insideRange && allowed) {
            NotificationHelper.show(applicationContext)
        }

        return Result.success()
    }
}