package com.security.zarpay.model
import androidx.compose.ui.graphics.Color

enum class TransactionStatus {
    SUCCESS, PROCESSING, FAILED
}

data class Transaction(
    val id: String,
    val name: String,
    val date: String,
    val amount: Int,
    val isCredit: Boolean,
    val status: TransactionStatus,
    val utr: String? = null,
    val statusNote: String? = null,
    val avatarColor: Color,
    val icon: String? = null
)

data class SpendingCategory(
    val label: String,
    val amount: Int,
    val color: Color
)
