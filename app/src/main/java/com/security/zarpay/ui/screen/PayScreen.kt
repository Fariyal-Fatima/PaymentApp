package com.security.zarpay.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.security.zarpay.ui.components.ContactListItem
import com.security.zarpay.ui.components.PayHeader
import com.security.zarpay.ui.components.PaySearchBar
import com.security.zarpay.ui.components.PaymentMethodsGrid
import com.security.zarpay.ui.model.Contact
import com.security.zarpay.ui.theme.LocalZarPayColors
import com.security.zarpay.ui.theme.ZarPayTheme
import com.security.zarpay.ui.viewmodel.PayViewModel
import com.security.zarpay.ui.viewmodel.PaymentState


@Composable
fun PayScreen(
    onContactSelected: () -> Unit = {},
    onBack: () -> Unit = {},
            payViewModel: PayViewModel = viewModel()
) {

    val colors = LocalZarPayColors.current
    val paymentState by payViewModel.paymentState.collectAsState()

    var selectedContact by remember { mutableStateOf<Contact?>(null) }
    var amount by remember { mutableStateOf("") }
    var mpin by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(paymentState) {
        if (paymentState is PaymentState.Success) {
            showDialog = false
            amount = ""
            mpin = ""
            payViewModel.resetState()
        }
    }
    val contacts = listOf(
        Contact("Niharika", "K8BsLgxcCyf5xZd3breOAXsvZNO2", 12, Color(0xFF6C63FF)),
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
        item { PayHeader(onBack = onBack) }

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
                onSendClick = {
                    selectedContact = contact
                    showDialog = true
                }
            )
            HorizontalDivider(
                color = colors.textSecondary.copy(0.15f),
                thickness = 0.5.dp,
                modifier = Modifier.padding(top = 14.dp, bottom = 2.dp)
            )

        }

    }


    if (showDialog && selectedContact != null) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Send to ${selectedContact!!.name}") },
            text = {
                Column {
                    OutlinedTextField(
                        value = amount,
                        onValueChange = { amount = it },
                        label = { Text("Amount (₹)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(12.dp))
                    OutlinedTextField(
                        value = mpin,
                        onValueChange = { if (it.length <= 4) mpin = it },
                        label = { Text("Enter MPIN") },
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)
                    )
                    if (paymentState is PaymentState.Error) {
                        Text(
                            text = (paymentState as PaymentState.Error).message,
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val amountValue = amount.toDoubleOrNull()
                        if (amountValue != null && amountValue > 0 && mpin.length == 4) {
                            payViewModel.sendPayment(
                                receiverName = selectedContact!!.name,
                                receiverId = selectedContact!!.upiId,
                                amount = amountValue,
                                enteredMpin = mpin
                            )
                        }
                    }
                ) {
                    if (paymentState is PaymentState.Loading) {
                        CircularProgressIndicator(modifier = Modifier.size(18.dp))
                    } else {
                        Text("Pay")
                    }
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PayScreenPreview() {
    ZarPayTheme {
        PayScreen()
    }
}