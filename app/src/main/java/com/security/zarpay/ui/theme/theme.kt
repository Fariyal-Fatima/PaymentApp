package com.security.zarpay.ui.theme

import androidx.compose.runtime.*

val LocalZarPayColors = staticCompositionLocalOf { themeFor(AppThemeMode.MIDNIGHT_BLUE) }

object ThemeManager {
    var currentTheme by mutableStateOf(AppThemeMode.MIDNIGHT_BLUE)
}

@Composable
fun ZarPayTheme(content: @Composable () -> Unit) {
    val colors = themeFor(ThemeManager.currentTheme)
    CompositionLocalProvider(LocalZarPayColors provides colors) {
        content()
    }
}
