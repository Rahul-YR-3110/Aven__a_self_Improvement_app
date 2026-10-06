package com.example.anchor

import android.content.Context

object ReminderSettings {

    private const val PREFS = "reminder_prefs"
    private const val KEY_START = "start_hour"
    private const val KEY_END = "end_hour"
    private const val KEY_INTERVAL = "interval_minutes"

    private fun prefs(context: Context) =
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)

    fun save(context: Context, startHour: Int, endHour: Int, intervalMinutes: Int) {
        prefs(context).edit()
            .putInt(KEY_START, startHour)
            .putInt(KEY_END, endHour)
            .putInt(KEY_INTERVAL, intervalMinutes)
            .apply()
    }

    fun startHour(context: Context) = prefs(context).getInt(KEY_START, 9)
    fun endHour(context: Context) = prefs(context).getInt(KEY_END, 18)
    fun intervalMinutes(context: Context) = prefs(context).getInt(KEY_INTERVAL, 60)
}