package com.security.zarpay.ui.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class QuickAction(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val iconColor: Color
)

