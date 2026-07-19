package com.security.zarpay.ui.model

import androidx.compose.ui.graphics.Color

data class Contact(
    val name: String,
    val upiId: String,
    val timesUsed: Int,
    val avatarColor: Color
) {
    val initials: String
        get() = name.split(" ")
            .take(2)
            .mapNotNull { it.firstOrNull()?.uppercase() }
            .joinToString("")
}