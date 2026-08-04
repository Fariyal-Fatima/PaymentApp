package com.security.zarpay.ui.theme

import androidx.compose.ui.graphics.Color

data class ZarPayColors(
    val background: Color, val cardBg : Color, val glassOverlay : Color,
    val accent: Color, val textPrimary: Color, val textSecondary: Color,
    val positive: Color, val negative: Color,
    val actionSend: Color, val actionScan: Color, val actionRequest: Color, val actionSplit: Color,
    val reminderBg: Color, val reminderAccent: Color,
    val balanceGradientStart: Color, val balanceGradientEnd: Color,
    val balanceTextColor: Color,
    val avatarBg: Color
)

enum class AppThemeMode { MIDNIGHT_BLUE, CLEAN_WHITE, MARVEL, ANIME, TERMINAL }

fun themeFor(mode: AppThemeMode): ZarPayColors = when (mode) {
    AppThemeMode.MIDNIGHT_BLUE -> ZarPayColors(
        MidnightBlueColors.background, MidnightBlueColors.cardBg, MidnightBlueColors.glassOverlay,
        MidnightBlueColors.accent, MidnightBlueColors.textPrimary, MidnightBlueColors.textSecondary,
        MidnightBlueColors.positive, MidnightBlueColors.negative,
        MidnightBlueColors.actionSend, MidnightBlueColors.actionScan, MidnightBlueColors.actionRequest, MidnightBlueColors.actionSplit,
        MidnightBlueColors.reminderBg, MidnightBlueColors.reminderAccent,
        MidnightBlueColors.balanceGradientStart, MidnightBlueColors.balanceGradientEnd, MidnightBlueColors.balanceTextColor,
        MidnightBlueColors.avatarBg
    )
    AppThemeMode.CLEAN_WHITE -> ZarPayColors(
        CleanWhiteColors.background, CleanWhiteColors.cardBg, CleanWhiteColors.glassOverlay,
        CleanWhiteColors.accent, CleanWhiteColors.textPrimary, CleanWhiteColors.textSecondary,
        CleanWhiteColors.positive, CleanWhiteColors.negative,
        CleanWhiteColors.actionSend, CleanWhiteColors.actionScan, CleanWhiteColors.actionRequest, CleanWhiteColors.actionSplit,
        CleanWhiteColors.reminderBg, CleanWhiteColors.reminderAccent,
        CleanWhiteColors.balanceGradientStart, CleanWhiteColors.balanceGradientEnd, CleanWhiteColors.balanceTextColor,
        CleanWhiteColors.avatarBg
    )


    else -> themeFor(AppThemeMode.MIDNIGHT_BLUE)
}