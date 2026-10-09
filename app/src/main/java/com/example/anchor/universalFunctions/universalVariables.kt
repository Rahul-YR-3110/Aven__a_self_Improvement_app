package com.example.anchor

import android.app.AppOpsManager
import android.content.Context
import android.os.Build
import android.os.Process
import androidx.core.content.edit

@Suppress("DEPRECATION")
fun checkUsageAccessPermission(context: Context): Boolean {
    val appOps = context.getSystemService(Context.APP_OPS_SERVICE) as? AppOpsManager ?: return false
    val mode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        appOps.unsafeCheckOpNoThrow(
            AppOpsManager.OPSTR_GET_USAGE_STATS,
            Process.myUid(),
            context.packageName
        )
    } else {
        appOps.checkOpNoThrow(
            AppOpsManager.OPSTR_GET_USAGE_STATS,
            Process.myUid(),
            context.packageName
        )
    }
    return mode == AppOpsManager.MODE_ALLOWED
}

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
    private const val KEY_USAGE_ACCESS_GRANTED = "usage_access_granted"

    fun saveUserName(context: Context, name: String) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit {
                putString(KEY_USER_NAME, name.trim())
                    .putBoolean(KEY_IS_LOGGED_IN, true)
            }
    }

    fun saveUsageAccessGranted(context: Context, isGranted: Boolean) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit {
                putBoolean(KEY_USAGE_ACCESS_GRANTED, isGranted)
            }
    }

    fun getUsageAccessGranted(context: Context): Boolean{
        return checkUsageAccessPermission(context)
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
