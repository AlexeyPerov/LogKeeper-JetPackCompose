package com.casualapps.logkeep.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightThemeColors = lightColors(
    primary = Tone700,
    primaryVariant = Tone900,
    onPrimary = Color.White,
    secondary = Tone700,
    secondaryVariant = Tone900,
    onSecondary = Color.White,
    error = Tone800
)

private val DarkThemeColors = darkColors(
    primary = Tone300,
    primaryVariant = Tone700,
    onPrimary = Color.Black,
    secondary = Tone300,
    onSecondary = Color.White,
    error = Tone200
)

@Composable
fun LogKeeperTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (darkTheme) DarkThemeColors else LightThemeColors,
        typography = LogKeeperTypography,
        shapes = LogKeeperShapes,
        content = content
    )
}
