package com.security.zarpay.ui.components

import android.location.GnssNavigationMessage
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.security.zarpay.ui.theme.LocalZarPayColors

@Composable
fun SmartReminderCard( message:String , subtitle: String) {
    val colors = LocalZarPayColors.current
    Row(
       modifier = Modifier.fillMaxWidth().glass(16).padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    )
    {
        Row (){
            (Icon(
                Icons.Default.NotificationsActive, contentDescription = "Notifications", tint = Color.White, modifier = Modifier.size(18.dp)
            ))
            Spacer(Modifier.width(10.dp))
            Text(message, color = Color.White)

        }
    }
}