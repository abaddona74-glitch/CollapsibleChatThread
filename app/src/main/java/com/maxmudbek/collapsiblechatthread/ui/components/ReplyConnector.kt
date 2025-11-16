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

/**
 * ReplyConnector draws a single vertical thread rail for the current comment level
 * and, when applicable, a horizontal elbow that connects the rail to the comment's
 * content padding. This is intended to be lightweight and suitable for deep recursion.
 *
 * Visual rules implemented:
 * - Exactly one rail per level (no multi-rail). The rail is drawn only if [hasReplies].
 * - Rail X aligns with the avatar center for this level and extends from the avatar
 *   center Y (24.dp) down to the bottom of the comment's content (measured height).
 * - Elbow is drawn only if this comment is itself a reply (level > 0). The elbow is
 *   a short 90-degree connector from the rail towards the content start.
 *
 * Geometry notes (defaults match the app UI):
 * - Content start for a level is: 36.dp + level * 16.dp
 * - Avatar size is 28.dp and the gap between avatar and content is 8.dp
 * - Therefore, railX = contentStart - (avatarGap + avatarSize/2)
 */
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
            val endY = size.height // dynamic: tracks collapse/expand height

            // Parent rail for child comments: ensures a rail exists for any level > 0
            if (level > 0) {
                drawLine(
                    color = railColor,
                    start = Offset(railXpx, 0f),
                    end = Offset(railXpx, endY),
                    strokeWidth = railWidth.toPx()
                )
            }

            // Self rail below avatar center only if this comment has replies
            if (hasReplies) {
                val startY = 24.dp.toPx() // avatar center Y within 48.dp header
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
