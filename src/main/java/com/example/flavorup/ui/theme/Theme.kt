package com.example.flavorup.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Cores baseadas no design do FlavorUp
val Orange500 = Color(0xFFFF8A65)
val Orange700 = Color(0xFFFF7043)
val Yellow100 = Color(0xFFFFF9C4)
val Yellow200 = Color(0xFFFFF59D)
val Cream = Color(0xFFFFFAF0)
val DarkGray = Color(0xFF424242)

private val LightColorScheme = lightColorScheme(
    primary = Orange500,
    onPrimary = Color.White,
    primaryContainer = Yellow100,
    onPrimaryContainer = DarkGray,
    secondary = Orange700,
    onSecondary = Color.White,
    background = Cream,
    onBackground = DarkGray,
    surface = Color.White,
    onSurface = DarkGray,
    surfaceVariant = Yellow200,
    onSurfaceVariant = DarkGray
)

private val DarkColorScheme = darkColorScheme(
    primary = Orange500,
    onPrimary = Color.White,
    primaryContainer = Orange700,
    onPrimaryContainer = Yellow100,
    secondary = Orange700,
    onSecondary = Color.White,
    background = Color(0xFF1C1B1F),
    onBackground = Color(0xFFE6E1E5),
    surface = Color(0xFF1C1B1F),
    onSurface = Color(0xFFE6E1E5)
)

@Composable
fun FlavorUpTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}