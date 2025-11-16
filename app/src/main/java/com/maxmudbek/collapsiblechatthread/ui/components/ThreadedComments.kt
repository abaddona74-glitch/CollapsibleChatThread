package com.maxmudbek.collapsiblechatthread.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.maxmudbek.collapsiblechatthread.R
import com.maxmudbek.collapsiblechatthread.model.Comment
import com.maxmudbek.collapsiblechatthread.ui.theme.ConnectorLine
import com.maxmudbek.collapsiblechatthread.ui.theme.OnSurfaceVar
import com.maxmudbek.collapsiblechatthread.ui.theme.Surface50
import com.maxmudbek.collapsiblechatthread.utils.TimeUtils

@Composable
fun CommentThread(
    comments: List<Comment>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(horizontal = 20.dp, vertical = 8.dp)
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = contentPadding
    ) {
        items(items = comments, key = { it.id }) { c ->
            CommentItem(comment = c, depth = 0)
        }
    }
}

@Composable
fun CommentItem(
    comment: Comment,
    depth: Int,
    modifier: Modifier = Modifier,
    avatarSize: Dp = 28.dp,
    avatarGap: Dp = 8.dp,
    headerHeight: Dp = 48.dp,
) {
    val indent = (depth * 12).dp
    val hasReplies = comment.replies.isNotEmpty()
    var expanded by rememberSaveable(comment.id) { mutableStateOf(false) }
    // Track the Y position of the actions row so rails can be capped at that point
    val actionsTopPx = remember { mutableStateOf<Float?>(null) }

    // Each comment block: horizontal 20dp + depth indent, vertical 33dp
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = indent + 20.dp, end = 20.dp, top = 33.dp, bottom = 33.dp)
            .drawBehind {
                val railX = avatarSize.toPx() / 2f
                // Cap rails at the actions row top (if measured) so they don't extend
                // past the elbow/connector area.
                val endY = actionsTopPx.value ?: size.height

                // Rail for replies (this comment as a child) — continuous from top
                if (depth > 0) {
                    drawLine(
                        color = ConnectorLine,
                        start = Offset(railX, 0f),
                        end = Offset(railX, endY),
                        strokeWidth = 1.dp.toPx()
                    )
                }
                // Self rail (only if this comment has replies) from avatar center down
                if (hasReplies) {
                    val startY = headerHeight.toPx() / 2f // avatar center at half header
                    drawLine(
                        color = ConnectorLine,
                        start = Offset(railX, startY),
                        end = Offset(railX, endY),
                        strokeWidth = 1.dp.toPx()
                    )
                }
            }
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Header area: 48dp high. Inside it: avatar 28x28, 12dp gap, and a 40dp content block
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ProfileIcon(initial = comment.authorInitial, modifier = Modifier.size(28.dp))
                Spacer(Modifier.width(12.dp))

                // Inner content block of height 40dp containing name row and (below) channel
                Column(modifier = Modifier.height(40.dp).fillMaxWidth()) {
                    // Name row: name (15sp semibold, lineHeight 20), small ellipse, and timestamp (12sp, AFB2B9)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = comment.authorName,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 15.sp, lineHeight = 20.sp)
                        )
                        Spacer(Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .size(4.dp)
                                .clip(androidx.compose.foundation.shape.CircleShape)
                                .background(Color(0xFFAFB2B9))
                        )
                        Spacer(Modifier.width(6.dp))
                        Text(
                            text = TimeUtils.getRelativeTime(comment.timestamp),
                            color = Color(0xFFAFB2B9),
                            style = TextStyle(fontSize = 12.sp, lineHeight = 16.sp)
                        )
                    }

                    // Channel (e.g. /ProductReflection) sits below the name row inside the 40dp block
                    if (depth == 0) {
                        comment.channel?.let { ch ->
                            Text(
                                text = ch,
                                color = OnSurfaceVar,
                                modifier = Modifier
                                    .padding(top = 6.dp)
                                    .background(Surface50, androidx.compose.foundation.shape.RoundedCornerShape(6.dp))
                                    .padding(vertical = 3.dp, horizontal = 6.dp),
                                style = TextStyle(fontWeight = FontWeight.Medium, fontSize = 10.sp, lineHeight = 12.sp)
                            )
                        }
                    }
                }
            }

            // Part 2: split area (left narrow rail + right text column) — render when we have both title and content
            if (depth == 0 && comment.title != null && comment.content.isNotEmpty()) {
                // Remember headline top Y in parent coordinates (px) so the rail can start aligned
                val headlineTopPx = remember { mutableStateOf<Float?>(null) }
                // Keep no extra top padding here so the overall comment vertical padding stays
                // at the outer block's 33.dp as specified.
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
                    // Left narrow column: 28dp wide with centered vertical rail (30% white)
                    Box(
                        modifier = Modifier
                            .width(28.dp)
                            .wrapContentHeight(Alignment.Top)
                            .drawBehind {
                                val cx = size.width / 2f
                                // Start the rail at the headline top if available, otherwise fall back
                                // to a safe header height to avoid overlapping the avatar.
                                val startY = headlineTopPx.value ?: headerHeight.toPx()
                                val endY = size.height
                                drawLine(
                                    color = Color(0x4DFFFFFF),
                                    start = Offset(cx, startY.coerceAtLeast(0f)),
                                    end = Offset(cx, endY),
                                    strokeWidth = 1.dp.toPx()
                                )
                            }
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    // Right column: headline + paragraph
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = comment.title ?: "",
                            modifier = Modifier.onGloballyPositioned { coords ->
                                // positionInParent() gives px offset relative to the Row parent.
                                headlineTopPx.value = coords.positionInParent().y
                            },
                            style = TextStyle(fontWeight = FontWeight.Medium, fontSize = 20.sp, lineHeight = 26.sp),
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = comment.content,
                            color = Color(0xFFAFB2B9),
                            style = TextStyle(fontSize = 15.sp, lineHeight = 20.sp),
                            maxLines = 4,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            } else {
                // Fallback: original title + content rendering
                if (depth == 0) {
                    comment.title?.let { t ->
                        Text(
                            text = t,
                            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                            style = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 18.sp, lineHeight = 24.sp)
                        )
                    }
                }

                // Content
                Text(
                    text = comment.content,
                    color = OnSurfaceVar,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Separator removed: the full-width divider created an extra horizontal line
            // that conflicted visually with the connector rails. Removed to match design.

            // Actions row (Part-3): fixed height 40dp. Left L-shaped connector aligned with avatar.
            val bg = MaterialTheme.colorScheme.background
            val iconTint = if (bg.luminance() < 0.5f) Color.White else Color.Black
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .height(40.dp)
                    .animateContentSize()
                    .onGloballyPositioned { coords ->
                        // positionInParent() gives px offset relative to the Column parent.
                        actionsTopPx.value = coords.positionInParent().y
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left area: reserve avatar width so connector aligns with avatar center.
                // When expanded, show the minus icon inside this box centered on the rail.
                val verticalLenDp = 20.dp
                val horizontalLenDp = 14.dp
                Box(
                    modifier = Modifier
                        .width(28.dp)
                        .fillMaxHeight()
                        .drawBehind {
                            val cx = size.width / 2f
                            val verticalLen = verticalLenDp.toPx()
                            val horizontalLen = horizontalLenDp.toPx()
                            // Draw L-shaped connector only when collapsed; when expanded we
                            // show the minus icon in this box instead and hide the L connector.
                            if (!expanded) {
                                // Mask a small horizontal strip at the elbow so any stray
                                // lines from parent drawing do not protrude past the elbow.
                                val maskHalf = 1.dp.toPx()
                                drawRect(
                                    color = bg,
                                    topLeft = Offset(0f, verticalLen - maskHalf),
                                    size = androidx.compose.ui.geometry.Size(size.width, maskHalf * 2f)
                                )

                                // Draw vertical segment (from top of actions box down verticalLen)
                                drawLine(
                                    color = Color(0x4DFFFFFF),
                                    start = Offset(cx, 0f),
                                    end = Offset(cx, verticalLen),
                                    strokeWidth = 1.dp.toPx()
                                )
                                // Draw horizontal segment (to the right) at y = verticalLen
                                drawLine(
                                    color = Color(0x4DFFFFFF),
                                    start = Offset(cx, verticalLen),
                                    end = Offset(cx + horizontalLen, verticalLen),
                                    strokeWidth = 1.dp.toPx()
                                )
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (hasReplies && expanded) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_minus_circle),
                            contentDescription = "Hide replies",
                            tint = Color.White,
                            modifier = Modifier
                                .size(20.dp)
                                .clickable { expanded = !expanded }
                        )
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Toggle group: when collapsed show icon+label; when expanded the minus icon
                // is rendered inside the left connector box, so we don't render the label here.
                if (hasReplies) {
                    val label = run {
                        val c = comment.getTotalReplyCount()
                        if (c == 1) "Show 1 reply" else "Show $c replies"
                    }

                    // Clicking anywhere on this Row toggles expand/collapse.
                    Row(
                        modifier = Modifier
                            .clickable { expanded = !expanded }
                            .wrapContentHeight(Alignment.CenterVertically),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (!expanded) {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_plus_circle),
                                contentDescription = label,
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(text = label, color = Color.White)
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))
                }

                // Reply group: icon + text with 8dp spacing
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_reply),
                        contentDescription = "Reply",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(text = "Reply", color = Color.White)
                }
            }

            // Children (conditional composition, no reserved space when collapsed)
            if (expanded && hasReplies) {
                Column(modifier = Modifier.animateContentSize()) {
                    comment.replies.forEach { child ->
                        CommentItem(comment = child, depth = depth + 1)
                    }
                }
            }
        }
    }
}

@Composable
fun ThreadLine(depth: Int, isLast: Boolean) {
    // Optional stub if needed later; current implementation draws lines inside CommentItem via drawBehind
}
