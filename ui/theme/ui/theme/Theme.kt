package com.eurochat.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val EuroChatDarkColorScheme = darkColorScheme(
    primary = PurpleAccent,
    secondary = ElectricBlue,
    background = DarkNavy,
    surface = CardBackground,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = TextPrimary,
    onSurface = TextPrimary
)

@Composable
fun EuroChatTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = EuroChatDarkColorScheme,
        content = content
    )
}
