package com.security.zarpay.ui.screen

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.graphics.Color
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
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
@Composable
fun PayScreen(
    onContactSelected: () -> Unit = {},
    onBack: () -> Unit = {}

) {

    val colors = LocalZarPayColors.current

    val contacts = listOf(
        Contact("Amit Mehta", "amit.mehta@sbi", 12, Color(0xFF6C63FF)),
        Contact("Priya Sharma", "priya.s@okaxis", 8, Color(0xFF00D9A3)),
        Contact("Sunil Kumar", "sunil.k@paytm", 5, Color(0xFFFF5C5C)),
        Contact("Ravi Gupta", "ravi.g@ybl", 3, Color(0xFFFFA726)),
        Contact("Neha Singh", "neha.singh@icici", 6, Color(0xFF00BCD4)),
    )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.background),
            contentPadding = PaddingValues(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item { PayHeader(onBack  = onBack) }

            item { PaySearchBar() }

            item { PaymentMethodsGrid() }

            item {
                androidx.compose.material3.Text(
                    text = "FREQUENT CONTACTS",
                    color = colors.textSecondary,
                    fontSize = 12.sp,
                    letterSpacing = 1.sp
                )
            }

            items(contacts) { contact ->
                ContactListItem(
                    contact = contact,
                    onSendClick = {  }
                )
                HorizontalDivider(
                    color = colors.textSecondary.copy(0.15f),
                    thickness = 0.5.dp,
                    modifier = Modifier.padding(top = 14.dp, bottom = 2.dp)
                )

            }

        }

}

@Preview(showBackground = true)
@Composable
fun PayScreenPreview() {
    ZarPayTheme {
        PayScreen()
    }
}