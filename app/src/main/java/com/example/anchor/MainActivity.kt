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
import androidx.compose.ui.Modifier
import com.example.anchor.notifications.NotificationHelper
import com.example.anchor.ui.pages.Screens.HabitTrackerScreen
import com.example.anchor.ui.pages.Screens.JournalScreen
import com.example.anchor.ui.pages.Screens.AppBlockerScreen
import com.example.anchor.ui.pages.water.WaterIntakeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NotificationHelper.createChannel(this)
        enableEdgeToEdge()
        setContent {
            AnchorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "home") {
                        composable("home") { HomeScreen(navController) }
                        composable("journals") { JournalScreen() }
                        composable("HabitTracker") { HabitTrackerScreen() }
                        composable("AppBlockerScreen") { AppBlockerScreen(navController = navController) }
                        composable("WaterIntakeScreen") { WaterIntakeScreen() }
                    }
            }
        }
    }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    AnchorTheme(darkTheme=true) {
        val fakeNavController = rememberNavController()
        HomeScreen(navController = fakeNavController)
    }
}

