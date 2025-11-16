package com.maxmudbek.collapsiblechatthread.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.painterResource
import com.maxmudbek.collapsiblechatthread.ui.theme.ConnectorLine
import com.maxmudbek.collapsiblechatthread.R


@Composable
fun VerticalConnectorLine(
    modifier: Modifier = Modifier
) {
    Canvas(
        modifier = modifier
            .width(2.dp)
            .fillMaxHeight()
    ) {
        drawLine(
            color = ConnectorLine,
            start = Offset(0f, 0f),
            end = Offset(0f, size.height),
            strokeWidth = 2.dp.toPx()
        )
    }
}


@Composable
fun HorizontalConnectorLine(
    modifier: Modifier = Modifier,
    length: Dp = 16.dp,
    height: Dp = 14.dp,
    thickness: Dp = 2.dp
) {
    Canvas(
        modifier = modifier
            .width(length)
            .height(height)
    ) {
        drawLine(
            color = ConnectorLine,
            start = Offset(0f, size.height / 2),
            end = Offset(size.width, size.height / 2),
            strokeWidth = thickness.toPx()
        )
    }
}


@Composable
fun ThreadVerticalLine(modifier: Modifier = Modifier) {
    
    Box(modifier = modifier) {}
}


@Composable
fun ToggleBubble(expanded: Boolean, onToggle: () -> Unit, modifier: Modifier = Modifier) {
    val bg = MaterialTheme.colorScheme.background
    val isDark = bg.luminance() < 0.5f
    val bubbleFill = if (isDark) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.surface
    val borderColor = ConnectorLine

    Box(
        modifier = modifier
            .size(24.dp)
            .clip(CircleShape)
            .background(bubbleFill, CircleShape)
            .border(1.25.dp, borderColor, CircleShape)
            .clickable { onToggle() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = if (expanded) R.drawable.icon_minus_circle else R.drawable.icon_plus_circle),
            contentDescription = if (expanded) "Collapse" else "Expand",
            tint = if (isDark) androidx.compose.ui.graphics.Color.White else androidx.compose.ui.graphics.Color.Black,
            modifier = Modifier.size(14.dp)
        )
    }
}
