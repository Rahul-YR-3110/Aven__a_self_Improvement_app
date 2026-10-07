package com.example.anchor

import android.content.Context
import androidx.core.content.edit

object ReminderSettings {
    private const val PREFS = "reminder_prefs"
    private const val KEY_START = "start_hour"
    private const val KEY_END = "end_hour"
    private const val KEY_INTERVAL = "interval_minutes"

    private fun prefs(context: Context) =
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)

    fun saveinterval(context: Context, startHour: Int, endHour: Int, intervalMinutes: Int) {
        prefs(context).edit {
            putInt(KEY_START, startHour)
                .putInt(KEY_END, endHour)
                .putInt(KEY_INTERVAL, intervalMinutes)
        }
    }

    fun startHour(context: Context) = prefs(context).getInt(KEY_START, 9)
    fun endHour(context: Context) = prefs(context).getInt(KEY_END, 18)
    fun intervalMinutes(context: Context) = prefs(context).getInt(KEY_INTERVAL, 60)
}
object UserPreferences {
    private const val PREFS_NAME = "user_prefs"
    private const val KEY_USER_NAME = "user_name"
    private const val KEY_IS_LOGGED_IN = "is_logged_in"

    fun saveUserName(context: Context, name: String) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit {
                putString(KEY_USER_NAME, name.trim())
                    .putBoolean(KEY_IS_LOGGED_IN, true)
            }
    }

    fun getUserName(context: Context): String {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getString(KEY_USER_NAME, "User") ?: "User"
    }

    fun isLoggedIn(context: Context): Boolean {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getBoolean(KEY_IS_LOGGED_IN, false)
    }
}