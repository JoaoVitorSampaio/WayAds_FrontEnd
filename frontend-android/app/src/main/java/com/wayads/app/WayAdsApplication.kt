package com.wayads.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.wayads.app.ui.theme.MobileappTheme
import com.wayads.ui.home.HomeScreen

class WayAdsApplication : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MobileappTheme {
                HomeScreen()
            }
        }
    }
}
