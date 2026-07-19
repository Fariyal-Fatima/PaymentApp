package com.security.zarpay.ui.components
import com.security.zarpay.ui.model.PaymentMethod
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.security.zarpay.ui.theme.LocalZarPayColors

@Composable
fun PaymentMethodCard(
    method: PaymentMethod,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = LocalZarPayColors.current

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .glassShine(cornerRadius = 16)
            .clickable { onClick() }
            .padding(14.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(method.iconBackgroundColor.copy(alpha = 0.2f), shape = RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = method.icon,
                contentDescription = method.title,
                tint = method.iconBackgroundColor.copy(4f),
                modifier = Modifier.size(20.dp)
            )
        }

        Column {
            Text(
                text = method.title,
                color = colors.textPrimary,
                fontSize = 15.sp
            )
            Text(
                text = method.subtitle,
                color = colors.textSecondary,
                fontSize = 12.sp
            )
        }
    }
}
