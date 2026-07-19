package com.security.zarpay.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.security.zarpay.model.SpendingCategory

private val CardBg = Color(0xFF141827)
private val TrackColor = Color(0xFF2A2F45)
private val LabelColor = Color(0xFF9BA1B0)

@Composable
fun SpendingCard(
    title: String = "JUNE SPENDING",
    categories: List<SpendingCategory>,
    modifier: Modifier = Modifier
) {
    val maxAmount = categories.maxOf { it.amount }.toFloat()

    Surface(
        modifier = modifier.fillMaxWidth().glassShine(7),
        color =Color.Transparent
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = title,
                color = LabelColor,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )

            categories.forEach { category ->
                SpendingRow(
                    category = category,
                    progress = category.amount / maxAmount
                )
            }
        }
    }
}

@Composable
private fun SpendingRow(category: SpendingCategory, progress: Float) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = category.label,
            color = Color.White,
            fontSize = 14.sp,
            modifier = Modifier.width(70.dp)
        )

        Canvas(
            modifier = Modifier
                .weight(1f)
                .height(8.dp)
        ) {
            // background track
            drawRoundRect(
                color = TrackColor,
                size = size,
                cornerRadius = CornerRadius(size.height / 2)
            )
            // foreground fill, proportional to progress
            drawRoundRect(
                color = category.color,
                size = Size(width = size.width * progress, height = size.height),
                cornerRadius = CornerRadius(size.height / 2)
            )
        }

        Text(
            text = "\u20B9${category.amount}",
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.width(70.dp)
        )
    }
}


