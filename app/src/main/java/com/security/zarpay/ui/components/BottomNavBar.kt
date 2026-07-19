package com.security.zarpay.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.rounded.Paid
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.security.zarpay.ui.theme.LocalZarPayColors

data class NavItem(val label: String, val route: String, val icon: ImageVector)

@Composable
fun BottomNavBar(selectedRoute: String, onNavigate: (String) -> Unit) {
    val colors = LocalZarPayColors.current
    val items = listOf(
        NavItem("Home", "home", Icons.Default.Home),
        NavItem("Pay", "pay", Icons.Rounded.Paid),
        NavItem("History", "history", Icons.Default.History),
        NavItem("Profile", "profile", Icons.Default.AccountCircle)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(colors.cardBg)
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        items.forEach { item ->
            val isSelected = selectedRoute == item.route
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable { onNavigate(item.route) }
            ) {
                Icon(
                    item.icon,
                    contentDescription = item.label,
                    tint = if (isSelected) colors.accent else colors.textSecondary,
                    modifier = Modifier.size(22.dp)
                )
                Text(
                    item.label,
                    color = if (isSelected) colors.accent else colors.textSecondary,
                    fontSize = 10.sp
                )
            }
        }
    }
}

