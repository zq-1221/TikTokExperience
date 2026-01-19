package com.example.tiktokexperience

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.example.tiktokexperience.ui.theme.TikTokExperienceTheme
import com.example.tiktokexperience.ui.screens.ExperienceScreen
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TikTokExperienceTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ExperienceScreen()
                }
            }
        }
    }
}