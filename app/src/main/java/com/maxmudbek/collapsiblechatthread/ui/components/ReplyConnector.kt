package com.maxmudbek.collapsiblechatthread.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.maxmudbek.collapsiblechatthread.ui.theme.ConnectorLine
import kotlin.math.min


@Composable
fun ReplyConnector(
    level: Int,
    hasReplies: Boolean,
    isExpanded: Boolean,
    modifier: Modifier = Modifier,
    railColor: Color = ConnectorLine,
    railWidth: Dp = 1.dp,
    headerHeight: Dp = 48.dp,
    avatarSize: Dp = 28.dp,
    avatarGap: Dp = 8.dp,
    contentStartBase: Dp = 36.dp,
    content: @Composable () -> Unit
) {
    val contentStart = contentStartBase + (level * 16).dp
    val railX = contentStart - (avatarGap + avatarSize / 2)

    androidx.compose.foundation.layout.Box(
        modifier = modifier.drawBehind {
            val railXpx = railX.toPx()
            val endY = size.height 

            
            if (level > 0) {
                drawLine(
                    color = railColor,
                    start = Offset(railXpx, 0f),
                    end = Offset(railXpx, endY),
                    strokeWidth = railWidth.toPx()
                )
            }

            
            if (hasReplies) {
                val startY = 24.dp.toPx() 
                drawLine(
                    color = railColor,
                    start = Offset(railXpx, startY),
                    end = Offset(railXpx, endY),
                    strokeWidth = railWidth.toPx()
                )
            }
        }
    ) {
        content()
    }
}
