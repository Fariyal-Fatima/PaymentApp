package com.security.zarpay.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.security.zarpay.ui.components.*
import com.security.zarpay.ui.theme.LocalZarPayColors
import com.security.zarpay.ui.theme.ZarPayTheme

data class HistoryItem(val name: String, val time: String, val amount: Double, val status: String, val refId: String)

@Composable
fun HomeScreen(onNavigate: (String) -> Unit = {}) {
    val colors = LocalZarPayColors.current
    var selectedTab by remember { mutableStateOf("home") }

    val transactions = listOf(
        HistoryItem("Amit Mehta", "Today 11:42", -500.0, "Success", "UTR 4821093"),
        HistoryItem("Priya Sharma", "Yesterday", -350.0, "Success", "UTR 4820991")
    )
    LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.background)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            item { TopBar(initials = "FF", name = "Fariyal Fatima") }
            item { BalanceCard(balance = "₹42,380.50", bankName = "SBI", lastFour = "4821") }
            item { SmartReminderCard(message = "Electricity bill due in 2 days","Pay") }
            item { QuickActionsSection(onSendClick = { onNavigate("pay") }) }
            item { ServicesSection() }
            item {

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Recent transactions", color = colors.textSecondary, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                    Text("See all >", color = colors.accent, fontSize = 12.sp)
                }
            }
            items(transactions) { tx -> TransactionRow(tx.name, tx.time, tx.amount, tx.status, tx.refId) }
        }
    }


@Preview(showBackground = true, backgroundColor = 0xFF0A1428)
@Composable
fun HomeScreenPreview() {
    ZarPayTheme {
        HomeScreen()
    }
}

