package com.security.zarpay.ui.components

import android.R
import android.R.attr.clickable
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBarDefaults.colors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.security.zarpay.ui.theme.LocalZarPayColors
import com.security.zarpay.ui.theme.ZarPayColors
import java.nio.file.WatchEvent

@Composable
fun PayHeader(onBack:() -> Unit,
              modifier: Modifier = Modifier
){
    val colors = LocalZarPayColors.current
    Row( verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier) {
            IconButton(onClick = {onBack()},
                modifier = Modifier.size(40.dp).glassShine(10)){
               Icon( modifier = modifier.size(20.dp),
                   imageVector = Icons.Default.ArrowBack,
                   contentDescription = "Back",
                   tint = colors.textPrimary)
            }
        Spacer(modifier.width(20.dp))
        Text(
           text = "Send Money",
            color = colors.textPrimary,
            fontSize = 20.sp,


        )
        }
    }


