
package com.security.zarpay.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.security.zarpay.ui.theme.LocalZarPayColors

@Composable
fun BalanceCard(balance: String, bankName: String, lastFour: String) {
    val colors = LocalZarPayColors.current
    var isVisible by remember { mutableStateOf(true) }

    Column(modifier = Modifier
        .fillMaxWidth()
        .clip(shape = RoundedCornerShape(size = 24.dp))
        .background(
            Brush.linearGradient(
                colors = listOf(colors.balanceGradientStart, colors.balanceGradientEnd)
            )
        )
        .padding(all = 20.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Your balance", color = colors.balanceTextColor.copy(alpha = 0.8f), fontSize = 12.sp)
            Spacer(Modifier.width(6.dp))
            IconButton(onClick = { isVisible = !isVisible }, modifier = Modifier.size(18.dp)) {
                Icon(
                    if (isVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                    contentDescription = "Toggle balance visibility",
                    tint = colors.balanceTextColor,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
        Spacer(Modifier.height(6.dp))
        Text(
            text = if (isVisible) balance else "₹ ••••••",
            color = colors.balanceTextColor,
            fontSize = 30.sp,
            fontWeight = FontWeight.ExtraBold
        )
        Spacer(Modifier.height(12.dp))
        Row(
            modifier = Modifier.glass(50).padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.CreditCard, contentDescription = null, tint = colors.textSecondary, modifier = Modifier.size(14.dp))
            Spacer(Modifier.width(6.dp))
            Text("$bankName •XXXXX$lastFour", color = colors.textSecondary, fontSize = 12.sp)
        }
        Spacer(Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .background(Color(0xFF1E8E5A).copy(alpha = 0.2f), shape = RoundedCornerShape(50))
                .padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.CheckCircle,
                contentDescription = null,
                tint = Color(0xFF4CAF50),
                modifier = Modifier.size(14.dp)
            )
            Spacer(Modifier.width(4.dp))
            Text("KYC Verified", color = Color(0xFF4CAF50), fontSize = 11.sp, fontWeight = FontWeight.Medium)
        }
    }
    }
