package com.goalwheel.demo.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val ZomatoRed = Color(0xFFE23744)
val ZomatoRedDark = Color(0xFFC42F3A)

private val DarkColorScheme = darkColorScheme(
    primary = ZomatoRed,
    onPrimary = Color.White,
    secondary = Color(0xFF6B7280),
    background = Color(0xFF0A0A0F),
    surface = Color(0xFF16161D),
    surfaceVariant = Color(0xFF1E1E26),
    onBackground = Color(0xFFF5F5F7),
    onSurface = Color(0xFFF5F5F7),
    outline = Color(0xFF2A2A35)
)

@Composable
fun GoalWheelTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        content = content
    )
}

