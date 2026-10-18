package com.gcaicedo.tasky.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = TaskyPurple,
    onPrimary = Color.White,
    primaryContainer = TaskyPurpleSoft,
    onPrimaryContainer = TaskyPurpleDark,
    secondary = TaskyPurpleDark,
    background = TaskyBackground,
    onBackground = Color(0xFF1C1B1F),
    surface = TaskySurface,
    onSurface = Color(0xFF1C1B1F),
    error = TaskyRed
)

@Composable
fun TaskyTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = Typography(),
        content = content
    )
}
