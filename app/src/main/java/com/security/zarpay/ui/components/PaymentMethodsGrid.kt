package com.security.zarpay.ui.components
import com.security.zarpay.ui.model.PaymentMethod
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.security.zarpay.ui.theme.LocalZarPayColors

@Composable
fun PaymentMethodsGrid(
    modifier: Modifier = Modifier
) {

    val colors = LocalZarPayColors.current
    val methods = listOf(
        PaymentMethod(Icons.Default.QrCodeScanner, "Scan QR", "Camera open", colors.actionScan),
        PaymentMethod(Icons.Default.Call, "By mobile", "Any number", colors.actionSend),
        PaymentMethod(Icons.Default.AlternateEmail, "UPI ID", "Enter @id",colors.actionRequest),
        PaymentMethod(Icons.Default.AccountBalance, "Bank a/c", "IFSC + acc", colors.actionSplit)
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        methods.chunked(2).forEach { rowItems ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                rowItems.forEach { method ->
                    PaymentMethodCard(
                        method = method,
                        onClick = { /* TODO: navigation/action */ },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}