package com.security.zarpay.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.security.zarpay.model.Transaction
import com.security.zarpay.model.TransactionStatus

private val SuccessGreen = Color(0xFF3DDC97)
private val FailedRed = Color(0xFFE8637A)
private val ProcessingOrange = Color(0xFFF5A623)
private val SubTextColor = Color(0xFF9BA1B0)
private val AmountRed = Color(0xFFE8637A)
private val AmountGreen = Color(0xFF3DDC97)

@Composable
fun TransactionItem(transaction: Transaction) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Avatar circle
        Surface(
            shape = CircleShape,
            color = transaction.avatarColor,
            modifier = Modifier.size(44.dp)
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                if (transaction.icon == "bolt") {
                    Icon(
                        imageVector = Icons.Filled.Bolt,
                        contentDescription = null,
                        tint = Color(0xFFFFA726)
                    )
                } else {
                    Text(
                        text = transaction.name.take(2).uppercase(),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }
        }

        // Name, date, status
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = transaction.name,
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
            Text(
                text = transaction.date,
                color = SubTextColor,
                fontSize = 12.sp
            )
            StatusRow(transaction)
        }

        // Amount + secondary info
        Column(horizontalAlignment = Alignment.End) {
            val amountColor = if (transaction.isCredit) AmountGreen else AmountRed
            val sign = if (transaction.isCredit) "+" else "-"
            Text(
                text = "$sign\u20B9${transaction.amount}",
                color = amountColor,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            val secondaryText = when {
                transaction.status == TransactionStatus.PROCESSING -> "Pending"
                transaction.statusNote == "Refunded" -> "Refunded"
                transaction.utr != null -> "UTR ${transaction.utr}"
                else -> null
            }
            secondaryText?.let {
                Text(text = it, color = SubTextColor, fontSize = 11.sp)
            }
        }
    }
}

@Composable
private fun StatusRow(transaction: Transaction) {
    val (icon, color, label) = when (transaction.status) {
        TransactionStatus.SUCCESS -> Triple(Icons.Filled.CheckCircle, SuccessGreen, "Success")
        TransactionStatus.PROCESSING -> Triple(Icons.Filled.Schedule, ProcessingOrange, "Processing")
        TransactionStatus.FAILED -> Triple(Icons.Filled.Close, FailedRed, transaction.statusNote ?: "Failed")
    }
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        Icon(imageVector = icon, contentDescription = null, tint = color, modifier = Modifier.size(12.dp))
        Text(text = label, color = color, fontSize = 12.sp)
    }
}

