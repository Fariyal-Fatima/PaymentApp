package com.security.zarpay.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.security.zarpay.ui.model.QuickAction
import com.security.zarpay.ui.theme.LocalZarPayColors

@Composable
fun QuickActionsSection(
    onSendClick: () ->Unit = {}
) {
    val colors = LocalZarPayColors.current
    val actions = listOf(
        QuickAction("Send", "Instant", Icons.AutoMirrored.Filled.Send, colors.actionSend),
        QuickAction("Scan", "QR Pay", Icons.Default.QrCodeScanner, colors.actionScan),
        QuickAction("Request", "Money", Icons.Default.ArrowDownward, colors.actionRequest),
        QuickAction("Split", "Group", Icons.Default.Groups, colors.actionSplit)
    )

    Column {
        Text("QUICK ACTIONS", color = colors.textSecondary, fontSize = 12.sp, fontWeight = FontWeight.Medium)
        Spacer(Modifier.height(8.dp))
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            actions.chunked(4).forEach { rowItems ->
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    rowItems.forEach { action ->
                        QuickActionCard(action, onClick = {
                            if (action.title == "Send") onSendClick()

                        },
                            modifier = Modifier.weight(1f)

                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun QuickActionCard(action: QuickAction,onClick : () -> Unit = {}, modifier: Modifier = Modifier) {
    val colors = LocalZarPayColors.current
    Column(

                modifier = modifier.padding(2.dp)
               .clickable{onClick()},

                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
        ) {
        Box(
            modifier = Modifier.size(65.dp).background(
                action.iconColor.copy(alpha = 0.2f), shape = RoundedCornerShape(15.dp)).border(1.dp,action.iconColor,RoundedCornerShape(15.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                action.icon,
                contentDescription = action.title,
                tint = action.iconColor,
                modifier = Modifier.size(28.dp).rotate(if(action.title=="Send") -45f else 0f)
            )
        }
        Spacer(Modifier.height(6.dp))
        Text(
            text = action.title,
            color = colors.textPrimary,
            fontSize = 12.sp
        )
    }
    }
