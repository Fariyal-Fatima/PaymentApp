package com.security.zarpay.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.security.zarpay.ui.theme.LocalZarPayColors

@Composable
fun Modifier.glass(cornerRadius: Int = 20): Modifier {
    val colors = LocalZarPayColors.current
    return this
        .background(colors.glassOverlay, RoundedCornerShape(cornerRadius.dp))
        .border(1.dp, Color.White.copy(alpha = 0.15f), RoundedCornerShape(cornerRadius.dp))
}
@Composable
fun Modifier.glassShine(cornerRadius: Int = 20): Modifier{
   return this
       .background(
           brush = Brush.verticalGradient(
           colors = listOf(
               Color.White.copy(0.15f),
               Color.White.copy(0.02f)
           )
       ),
        shape = RoundedCornerShape(cornerRadius.dp)
       )
       .border(1.dp,Color.White.copy(0.15f),RoundedCornerShape(cornerRadius))
}