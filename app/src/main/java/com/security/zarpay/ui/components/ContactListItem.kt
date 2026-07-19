package com.security.zarpay.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.security.zarpay.ui.model.Contact
import com.security.zarpay.ui.theme.LocalZarPayColors

@Composable
fun ContactListItem(
    contact: Contact,
    onSendClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = LocalZarPayColors.current

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(contact.avatarColor.copy(alpha = 0.25f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = contact.initials,
                color = contact.avatarColor,
                fontSize = 14.sp
            )
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(text = contact.name, color = colors.textPrimary, fontSize = 15.sp)
            Text(text = contact.upiId, color = colors.textSecondary, fontSize = 12.sp)
        }

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .glass(20)
                .padding(horizontal = 10.dp, vertical = 4.dp)
        ) {
            Text(
                text = "${contact.timesUsed} times",
                color = colors.accent,
                fontSize = 11.sp
            )
        }


            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .glass(10)
                    .clickable{onSendClick()},
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Send to ${contact.name}",
                    tint = colors.accent,
                    modifier = Modifier.size(16.dp).rotate(-45f)
                )
            }

    }
}