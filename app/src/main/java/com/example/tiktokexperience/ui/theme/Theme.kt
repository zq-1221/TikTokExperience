package com.example.tiktokexperience.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.compose.ui.graphics.Color

// 抖音风格的配色方案
private val TikTokDarkColorScheme = darkColorScheme(
    primary = TikTokRed,
    secondary = TikTokDarkGray,
    tertiary = Color(0xFF4A4A4A),
    background = TikTokBlack,
    surface = TikTokDarkGray,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = TikTokTextDark,
    onSurface = TikTokTextDark
)

private val TikTokLightColorScheme = lightColorScheme(
    primary = TikTokRed,
    secondary = TikTokLightGray,
    tertiary = Color(0xFFE0E0E0),
    background = Color.White,
    surface = TikTokLightGray,
    onPrimary = Color.White,
    onSecondary = TikTokTextLight,
    onTertiary = TikTokTextLight,
    onBackground = TikTokTextLight,
    onSurface = TikTokTextLight
)

@Composable
fun TikTokExperienceTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> TikTokDarkColorScheme
        else -> TikTokLightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}