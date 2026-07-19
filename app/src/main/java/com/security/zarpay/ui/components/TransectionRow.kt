package com.security.zarpay.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.security.zarpay.ui.theme.LocalZarPayColors

@Composable
fun TransactionRow(name: String, time: String, amount: Double, status: String, refId: String) {
    val colors = LocalZarPayColors.current
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.size(38.dp).background(colors.accent.copy(alpha = 0.2f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(name.take(2).uppercase(), color = colors.accent, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.width(10.dp))
            Column {
                Text(name, color = colors.textPrimary, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                Text("$time · $status", color = colors.positive, fontSize = 11.sp)
            }
        }
        Column(horizontalAlignment = Alignment.End) {
            Spacer(modifier = Modifier.height(1.dp))
            Text(
                if (amount >= 0) "+₹${"%.0f".format(amount)}" else "-₹${"%.0f".format(-amount)}",
                color = if (amount >= 0) colors.positive else colors.negative,
                fontWeight = FontWeight.Bold, fontSize = 14.sp
            )
            Text(refId, color = colors.textSecondary, fontSize = 10.sp)
        }
    }
}

