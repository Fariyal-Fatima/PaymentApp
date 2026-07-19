package com.security.zarpay.ui.components

import android.R
import android.graphics.Paint
import androidx.collection.intIntMapOf
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.CheckboxDefaults.colors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.security.zarpay.ui.theme.LocalZarPayColors
import com.security.zarpay.ui.model.QuickAction
import java.nio.file.WatchEvent

@Composable
fun ServicesSection(){
 val colors = LocalZarPayColors.current
 val actions = listOf(
     QuickAction("Recharge", "Mobile DTH", Icons.Default.Phone, colors.actionSend),
     QuickAction("Bill Pay", "Electric,Gas", Icons.Default.Bolt, colors.actionScan ),
     QuickAction("Schedule", "Auto pay",Icons.Default.Schedule,colors.actionRequest),
     QuickAction("Offers","Cashback", Icons.Default.CardGiftcard,colors.actionRequest)

 )
    Column {
        Text("SERVICES", color = colors.textSecondary, fontSize = 12.sp, fontWeight = FontWeight.Medium)
        Spacer(Modifier.height(8.dp))
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            actions.chunked(2).forEach {  rowItems ->
          Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
              rowItems.forEach { action -> ServiceActionCard(action, Modifier.weight(1f)) }
          }
            }
        }
    }
}
@Composable
private fun ServiceActionCard(action: QuickAction, modifier : Modifier= Modifier) {
    val colors = LocalZarPayColors.current
    Column(
        modifier = modifier.padding(2.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.size(width = 170.dp, height = 70.dp).glass(20).padding(horizontal = 12.dp)
            ,
            contentAlignment = Alignment.CenterStart

        ) {
            Row() {
                Box(
                    modifier = Modifier.size(40.dp)
                        .background(action.iconColor.copy(0.2f), shape = RoundedCornerShape(15.dp)),
                       contentAlignment = Alignment.Center
                )
                {
                    Icon(
                        action.icon,
                        contentDescription = action.title,
                        tint = action.iconColor,
                        modifier = Modifier.size(25.dp),
                    )
                }

                Spacer(Modifier.width(12.dp))
                Column( modifier = Modifier.padding(top = 4.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = action.title, color = colors.textPrimary,
                        fontWeight = FontWeight.Bold, fontSize = 13.sp
                    )
                    Spacer(Modifier.height(3.dp))
                    if (action.subtitle.isNotEmpty()) {
                        Text(text = action.subtitle, color = colors.textSecondary, fontSize = 11.sp)
                    }
                }
            }

        }
    }
}