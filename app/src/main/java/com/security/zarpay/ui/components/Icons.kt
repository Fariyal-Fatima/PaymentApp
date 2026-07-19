package com.security.zarpay.ui.components

import android.app.Notification
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.CurrencyRupee
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Paid
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.rounded.Paid
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.security.zarpay.ui.model.QuickAction
import com.security.zarpay.ui.theme.LocalZarPayColors

@Composable
fun Icons(
    onNavigate: (String) -> Unit = {}
){
val colors = LocalZarPayColors
    val actions = listOf(
        QuickAction("Home", "Instant", Icons.Default.Home, Color.White),
        QuickAction("  Pay", "QR Pay", Icons.Rounded.Paid, Color.White),
        QuickAction("History", "Money", Icons.Default.History, Color.White),
        QuickAction("Profile", "Group", Icons.Default.AccountCircle, Color.White)
    )
    Column {
        Spacer(Modifier.height(4.dp))
        Column(verticalArrangement = Arrangement.spacedBy(17.dp)) {
            actions.chunked(4).forEach { rowItems ->
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    rowItems.forEach { action ->
                        Icons(
                            action,
                            onClick = { onNavigate(action.title.trim().lowercase())},
                            modifier = Modifier.weight(1f).padding(5.dp)
                        )

                }}

            }
        }
    }
}
@Composable
 private fun Icons(action: QuickAction,
                   onClick: () ->Unit,
                   modifier: Modifier = Modifier){
    val colors = LocalZarPayColors.current
    Row(  modifier = modifier.padding(horizontal = 15.dp)
        .clickable{onClick()},
        horizontalArrangement = Arrangement.Center

          )
    {
        Column(
            verticalArrangement = Arrangement.Center,
        ) {
            Box(
                modifier = Modifier.size(35.dp),
                contentAlignment = Alignment.Center

            ) {
                Icon(
                    action.icon,
                    contentDescription = action.title,
                    tint = action.iconColor,
                    modifier = Modifier.size(28.dp)
                )
            }
            Spacer(Modifier.height(1.dp))
            Text(
                text = action.title,
                color = colors.textPrimary,
                fontSize = 12.sp
            )
        }
    }
}