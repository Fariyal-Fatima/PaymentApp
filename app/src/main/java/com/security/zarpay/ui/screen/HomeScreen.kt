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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuth
import com.security.zarpay.ui.components.*
import com.security.zarpay.ui.theme.LocalZarPayColors
import com.security.zarpay.ui.theme.ZarPayTheme
import com.security.zarpay.ui.viewmodel.HomeViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import com.security.zarpay.util.formatRupees

data class HistoryItem(val name: String, val time: String, val amount: Long, val status: String, val refId: String)

@Composable
fun HomeScreen(
    onNavigate: (String) -> Unit = {},
    homeViewModel: HomeViewModel = viewModel()
) {
    val colors = LocalZarPayColors.current
    var selectedTab by remember { mutableStateOf("home") }
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
    val uiState by homeViewModel.uiState.collectAsState()

    LaunchedEffect(userId) {
        if (userId.isNotEmpty()) {
            homeViewModel.loadData(userId)
        }
    }

    LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.background)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
        item { TopBar(initials = uiState.user?.initials ?: "", name = uiState.user?.name ?: "") }
        item { BalanceCard(
            balance = formatRupees(uiState.user?.balance ?: 0L),
            bankName = uiState.user?.bankName ?: "",
            lastFour = uiState.user?.lastFour ?: ""
        ) }
            item { SmartReminderCard(message = "Electricity bill due in 2 days","Pay") }
            item { QuickActionsSection(onSendClick = { onNavigate("pay") }) }
            item { ServicesSection() }
            item {

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Recent transactions", color = colors.textSecondary, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                    Text("See all >", color = colors.textPrimary, fontSize = 12.sp)
                }
            }
        items(items = uiState.transactions) { tx ->
            TransactionRow(tx.name, formatTimestamp(tx.timestamp), tx.amount, tx.status, tx.refId)
        }
        }
    }
fun formatTimestamp(timestamp: Long): String {
    val sdf = java.text.SimpleDateFormat("hh:mm a", java.util.Locale.getDefault())
    val today = java.util.Calendar.getInstance()
    val txnDate = java.util.Calendar.getInstance().apply { timeInMillis = timestamp }

    return when {
        today.get(java.util.Calendar.DAY_OF_YEAR) == txnDate.get(java.util.Calendar.DAY_OF_YEAR) ->
            "Today ${sdf.format(java.util.Date(timestamp))}"
        today.get(java.util.Calendar.DAY_OF_YEAR) - txnDate.get(java.util.Calendar.DAY_OF_YEAR) == 1 ->
            "Yesterday"
        else -> java.text.SimpleDateFormat("dd MMM", java.util.Locale.getDefault()).format(java.util.Date(timestamp))
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0A1428)
@Composable
fun HomeScreenPreview() {
    ZarPayTheme {
        HomeScreen()
    }
}

