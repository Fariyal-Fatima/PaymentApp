package com.security.zarpay.ui.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerIcon

data class PaymentMethod(
    val icon : ImageVector,
    val title: String,
    val subtitle: String,
    val iconBackgroundColor: Color
)