package com.security.zarpay.model

import androidx.compose.ui.graphics.vector.ImageVector

data class ProfileMenuItem(
    val icon: ImageVector,
    val iconBgColor: androidx.compose.ui.graphics.Color,
    val iconTint: androidx.compose.ui.graphics.Color,
    val label: String,
    val badge: String? = null, // e.g. "1 active", "New"
    val badgeColor: androidx.compose.ui.graphics.Color? = null,
    val onClick: () -> Unit = {}
)

data class ProfileStat(
    val value: String,
    val label: String
)