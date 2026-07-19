package com.security.zarpay.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBarDefaults.colors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.security.zarpay.model.SpendingCategory
import com.security.zarpay.model.Transaction
import com.security.zarpay.model.TransactionStatus
import com.security.zarpay.ui.components.BottomNavBar
import com.security.zarpay.ui.components.FilterChipRow
import com.security.zarpay.ui.components.PayHeader
import com.security.zarpay.ui.components.SpendingCard
import com.security.zarpay.ui.components.StatementCard
import com.security.zarpay.ui.components.TransactionItem
import com.security.zarpay.ui.theme.ZarPayTheme
import com.security.zarpay.ui.components.Icons as BottomNavBar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.security.zarpay.ui.components.BottomNavBar
import com.security.zarpay.ui.components.ContactListItem
import com.security.zarpay.ui.components.Icons
import com.security.zarpay.ui.components.PayHeader
import com.security.zarpay.ui.components.PaySearchBar
import com.security.zarpay.ui.components.PaymentMethodsGrid
import com.security.zarpay.ui.model.Contact
import com.security.zarpay.ui.theme.LocalZarPayColors
import com.security.zarpay.ui.theme.ZarPayTheme
private val ScreenBg = Color(0xFF0B0E1A)

@Composable
fun HistoryScreen(
    onBack: () -> Unit = {},
    onNavigate: (String) -> Unit = {}
) {

    val colors = LocalZarPayColors.current
    var selectedFilter by remember { mutableStateOf("All") }

    val spendingCategories = remember {
        listOf(
            SpendingCategory("Food", 3600, Color(0xFF7C6CF0)),
            SpendingCategory("Bills", 2750, Color(0xFF3DDC97)),
            SpendingCategory("Transfer", 2000, Color(0xFFF5A623)),
            SpendingCategory("Other", 1200, Color(0xFFE8916B))
        )
    }

    val allTransactions = remember {
        listOf(
            Transaction(
                id = "1",
                name = "Amit Mehta",
                date = "Today, 11:42 AM",
                amount = 500,
                isCredit = false,
                status = TransactionStatus.SUCCESS,
                utr = "4821093",
                avatarColor = Color(0xFF4A3F8C)
            ),
            Transaction(
                id = "2",
                name = "Priya Sharma",
                date = "Yesterday",
                amount = 1200,
                isCredit = true,
                status = TransactionStatus.SUCCESS,
                utr = "3910284",
                avatarColor = Color(0xFF1E5C4A)
            ),
            Transaction(
                id = "3",
                name = "UPPCL Electric",
                date = "Jun 8",
                amount = 1240,
                isCredit = false,
                status = TransactionStatus.PROCESSING,
                avatarColor = Color(0xFF3A2E1A),
                icon = "bolt"
            ),
            Transaction(
                id = "4",
                name = "Ravi Gupta",
                date = "Jun 6",
                amount = 2000,
                isCredit = false,
                status = TransactionStatus.FAILED,
                statusNote = "Failed — Bank timeout",
                avatarColor = Color(0xFF5C2A2A)
            )
        )
    }

    // Filter logic: match the chip to transaction status/type
    val filteredTransactions = when (selectedFilter) {
        "Sent" -> allTransactions.filter { !it.isCredit }
        "Received" -> allTransactions.filter { it.isCredit }
        "Bills" -> allTransactions.filter { it.name.contains("Electric") || it.name.contains("Bill") }
        "Failed" -> allTransactions.filter { it.status == TransactionStatus.FAILED }
        else -> allTransactions
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
    ) {
        Text(
            text = "History",
            color = Color.White,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 20.dp, top = 24.dp, bottom = 8.dp)
        )

        FilterChipRow(
            selectedFilter = selectedFilter,
            onFilterSelected = { selectedFilter = it }
        )

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(4.dp))
                    SpendingCard(categories = spendingCategories)
                }
                item {
                    StatementCard(onDownloadClick = { /* TODO: hook up PDF generation */ })
                }
                item {
                    Text(
                        text = "THIS MONTH",
                        color = Color(0xFF9BA1B0),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
                items(filteredTransactions, key = { it.id }) { transaction ->
                    TransactionItem(transaction)

                }
                item { Spacer(modifier = Modifier.height(12.dp)) }
            }

        }
    }

    @Preview(showBackground = true)
    @Composable
    fun HistoryScreenPreview() {
        ZarPayTheme {
            HistoryScreen()
        }
    }