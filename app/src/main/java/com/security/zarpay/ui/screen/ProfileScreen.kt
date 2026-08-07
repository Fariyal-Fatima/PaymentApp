package com.security.zarpay.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBarDefaults.colors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.security.zarpay.model.ProfileMenuItem
import com.security.zarpay.model.ProfileStat
import com.security.zarpay.ui.components.BottomNavBar
import com.security.zarpay.ui.components.ProfileHeaderCard
import com.security.zarpay.ui.components.ProfileMenuItemRow
import com.security.zarpay.ui.components.ProfileStatsRow
import com.security.zarpay.ui.theme.LocalZarPayColors
import com.security.zarpay.ui.theme.ZarPayTheme
import com.security.zarpay.ui.components.Icons as BottomNavBar
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import com.security.zarpay.ui.theme.AppThemeMode
import com.security.zarpay.ui.theme.ThemeManager
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip

private val ScreenBg = Color(0xFF0B0E1A)

@Composable
fun ProfileScreen(
    userName: String = "Fariyal Fatima",
    onNavigate: (String) -> Unit = {},
    onLogout: () -> Unit = {}

) {

    val colors = LocalZarPayColors.current
    val stats = remember {
        listOf(
            ProfileStat(value = "\u20B99.5K", label = "Sent this month"),
            ProfileStat(value = "24", label = "Transactions")
        )
    }

    val menuItems = remember {
        listOf(
            ProfileMenuItem(
                icon = Icons.Filled.AccountBalance,
                iconBgColor = Color(0xFF3DDC97).copy(alpha = 0.15f),
                iconTint = Color(0xFF3DDC97),
                label = "Linked bank accounts",
                badge = "1 active",
                badgeColor = Color(0xFF1E5C4A)
            ),
            ProfileMenuItem(
                icon = Icons.Filled.Shield,
                iconBgColor = Color(0xFF7C6CF0).copy(alpha = 0.15f),
                iconTint = Color(0xFF7C6CF0),
                label = "Privacy & security",
                badge = "New",
                badgeColor = Color(0xFF4A3F8C)
            ),
            ProfileMenuItem(
                icon = Icons.Filled.PhoneAndroid,
                iconBgColor = Color(0xFFF5A623).copy(alpha = 0.15f),
                iconTint = Color(0xFFF5A623),
                label = "Trusted devices"
            ),
            ProfileMenuItem(
                icon = Icons.Filled.Translate,
                iconBgColor = Color(0xFFE8637A).copy(alpha = 0.15f),
                iconTint = Color(0xFFE8637A),
                label = "Language — Hindi, English"
            ),
            ProfileMenuItem(
                icon = Icons.Filled.Palette,
                iconBgColor = Color(0xFF3DDC97).copy(alpha = 0.15f),
                iconTint = Color(0xFF3DDC97),
                label = "Mode — Dark, Light"
            ),
            ProfileMenuItem(
                icon = Icons.Filled.Description,
                iconBgColor = Color(0xFF7C6CF0).copy(alpha = 0.15f),
                iconTint = Color(0xFF7C6CF0),
                label = "Download all statements"
            ),
            ProfileMenuItem(
                icon = Icons.Filled.SupportAgent,
                iconBgColor = Color(0xFFF5A623).copy(alpha = 0.15f),
                iconTint = Color(0xFFF5A623),
                label = "Support — 24/7 Live chat"
            )

        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
    ) {
        Text(
            text = "Profile",
            color = colors.textPrimary,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 20.dp, top = 24.dp, bottom = 16.dp)
        )

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    ProfileHeaderCard(
                        name = userName,
                        subtitle = "fatima.fariyal \u00B7 9876543210",
                        isKycVerified = true
                    )
                }
                item {
                    ProfileStatsRow(stats = stats)
                }

                item {
                    Text(
                        text = "App Theme",
                        color = colors.textSecondary,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        ThemeChip(
                            label = "Dark",
                            isSelected = ThemeManager.currentTheme == AppThemeMode.MIDNIGHT_BLUE,
                            onClick = { ThemeManager.currentTheme = AppThemeMode.MIDNIGHT_BLUE },
                            colors = colors
                        )
                        ThemeChip(
                            label = "Light",
                            isSelected = ThemeManager.currentTheme == AppThemeMode.CLEAN_WHITE,
                            onClick = { ThemeManager.currentTheme = AppThemeMode.CLEAN_WHITE },
                            colors = colors
                        )
                        ThemeChip(
                            label = "Hero",
                            isSelected = ThemeManager.currentTheme == AppThemeMode.MARVEL,
                            onClick = { ThemeManager.currentTheme = AppThemeMode.MARVEL },
                            colors = colors
                        )

                    }
                }

                items(menuItems) { menuItem ->
                    ProfileMenuItemRow(item = menuItem)
                }

                item {
                    Button(
                        onClick = { onLogout() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE8637A))
                    ) {
                        Icon(Icons.Filled.ExitToApp, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("Logout")
                    }
                }
                item { Spacer(modifier = Modifier.height(12.dp)) }
            }
        }
    }
@Composable
fun ThemeChip(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    colors: com.security.zarpay.ui.theme.ZarPayColors
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(if (isSelected) colors.accent else colors.cardBg)
            .clickable { onClick() }
            .padding(horizontal = 18.dp, vertical = 10.dp)
    ) {
        Text(
            text = label,
            color = if (isSelected) Color.White else colors.textSecondary,
            fontSize = 13.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

    @Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ZarPayTheme {
        ProfileScreen()
    }
}
