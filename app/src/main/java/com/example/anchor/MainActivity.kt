package com.example.anchor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import com.example.anchor.ui.theme.AnchorTheme
import androidx.compose.ui.tooling.preview.Preview
import com.example.anchor.ui.pages.Screens.HomeScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.anchor.universalFunctions.NotificationHelper
import com.example.anchor.ui.pages.Screens.HabitTrackerScreen
import com.example.anchor.ui.pages.Screens.JournalScreen
import com.example.anchor.ui.pages.Screens.AppBlockerScreen
import com.example.anchor.ui.pages.water.WaterIntakeScreen

import com.example.anchor.ui.pages.Onboarding.LoginScreen
import com.example.anchor.ui.pages.Screens.BreathingScreen
import com.example.anchor.ui.pages.Screens.ProfileScreen
import com.example.anchor.ui.pages.Screens.TasksTrackerScreen
import com.example.anchor.ui.pages.Screens.UsageAccessScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NotificationHelper.createChannel(this)
        enableEdgeToEdge()
        val startDest = if (UserPreferences.isLoggedIn(this)) "home" else "login"
        setContent {
            var isDark by remember { mutableStateOf(true)}
            val usageaccess = UserPreferences.getUsageAccessGranted(this)
            AnchorTheme(darkTheme = isDark) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = startDest) {
                        composable("login") { LoginScreen(navController = navController) }
                        composable("home") { HomeScreen(navController, usageaccess) }
                        composable("journals") { JournalScreen(navController = navController) }
                        composable("HabitTracker") { HabitTrackerScreen(navController = navController) }
                        composable("AppBlockerScreen") { AppBlockerScreen(navController = navController) }
                        composable("WaterIntakeScreen") { WaterIntakeScreen() }
                        composable("TasksTrackerScreen"){ TasksTrackerScreen(navController = navController) }
                        composable("ProfilePage"){ ProfileScreen(isDark = isDark, themeChange = { isDark = it },navController = navController) }
                        composable("UsageAccessScreen") { UsageAccessScreen(navController = navController) }
                        composable("BreathingScreen"){BreathingScreen()}
                    }
            }
        }
    }
    }
}


