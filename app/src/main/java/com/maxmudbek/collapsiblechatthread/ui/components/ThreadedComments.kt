package com.maxmudbek.collapsiblechatthread.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.maxmudbek.collapsiblechatthread.ui.theme.SurfaceAlt30
import com.maxmudbek.collapsiblechatthread.utils.TimeUtils
import androidx.compose.ui.semantics.Role

@Composable
fun CommentThread(
    comments: List<Comment>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(horizontal = 0.dp, vertical = 8.dp)
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = contentPadding
    ) {
        itemsIndexed(items = comments, key = { _, item -> item.id }) { index, c ->
            CommentItem(comment = c, depth = 0, isFirst = index == 0)
        }
    }
}

@Composable
fun CommentItem(
    comment: Comment,
    depth: Int,
    isFirst: Boolean = false,
    modifier: Modifier = Modifier,
    avatarSize: Dp = 24.dp,
    avatarGap: Dp = 8.dp,
    headerHeight: Dp = 48.dp,
) {
    val indent = (depth * 12).dp
    val hasReplies = comment.replies.isNotEmpty()
    var expanded by rememberSaveable(comment.id) { mutableStateOf(false) }

    val actionsTopPx = remember { mutableStateOf<Float?>(null) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                enabled = comment.replies.isNotEmpty(),
                role = Role.Button,
                onClick = { expanded = !expanded }
            )
            .padding(
                start = indent + 20.dp,
                end = 20.dp,
                top = if (depth == 0 && isFirst) 33.dp else 0.dp,
                bottom = 0.dp
            )
            .drawBehind {
                val railX = avatarSize.toPx() / 2f
                val avatarCenterY = avatarSize.toPx() / 2f
                val bottomY = size.height

                // Draw a continuous vertical rail for nested threads or when replies exist.
                if (depth > 0 || hasReplies) {
                    drawLine(
                        color = ConnectorLine,
                        start = Offset(railX, 0f),
                        end = Offset(railX, bottomY),
                        strokeWidth = 1.dp.toPx()
                    )
                }

                // Draw connector segment from avatar center down to the actions area when replies exist.
                if (hasReplies) {
                    val endY = (actionsTopPx.value ?: bottomY) + 3.dp.toPx()
                    drawLine(
                        color = ConnectorLine,
                        start = Offset(railX, avatarCenterY),
                        end = Offset(railX, endY.coerceAtMost(bottomY)),
                        strokeWidth = 1.dp.toPx()
                    )
                }

                // Draw horizontal ticks for both replies and nested replies (depth > 0)
                if (hasReplies || depth > 0) {
                    val armLen = 6.dp.toPx()
                    // left short tick (inside rail area)
                    drawLine(
                        color = ConnectorLine,
                        start = Offset(railX - armLen, avatarCenterY),
                        end = Offset(railX, avatarCenterY),
                        strokeWidth = 1.dp.toPx()
                    )
                    // right short arm pointing toward the avatar/content area
                    drawLine(
                        color = ConnectorLine,
                        start = Offset(railX, avatarCenterY),
                        end = Offset(railX + armLen, avatarCenterY),
                        strokeWidth = 1.dp.toPx()
                    )
                }
            }
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(Alignment.Top),
                verticalAlignment = Alignment.Top
            ) {
                ProfileIcon(initial = comment.authorInitial, modifier = Modifier.size(avatarSize))
                Spacer(Modifier.width(12.dp))

                Column(modifier = Modifier.fillMaxWidth()) {
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

                    if (comment.tags.isNotEmpty()) {
                        comment.tags.forEach { tag ->
                            Spacer(Modifier.height(4.dp))
                            val isTopCommenter = tag.equals("Top 1% Commenter", ignoreCase = true)
                            val isTopPoster = tag.equals("Top 1% Poster", ignoreCase = true)

                            val tagTextColor = when {
                                isTopPoster -> Color(0xFF68C3FF)
                                isTopCommenter -> Color(0xFFFFC368)
                                else -> OnSurfaceVar
                            }

                            val tagBgColor = when {
                                isTopPoster -> Color(0x1F68C3FF)
                                isTopCommenter -> Color(0x1FFFC368)
                                else -> Surface50
                            }

                            Box(modifier = Modifier.padding(top = 2.dp)) {
                                Text(
                                    text = tag,
                                    color = tagTextColor,
                                    modifier = Modifier
                                        .background(
                                            tagBgColor,
                                            androidx.compose.foundation.shape.RoundedCornerShape(6.dp)
                                        )
                                        .padding(vertical = 3.dp, horizontal = 6.dp),
                                    style = TextStyle(fontWeight = FontWeight.Medium, fontSize = 10.sp, lineHeight = 12.sp)
                                )
                            }
                        }
                    }

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

            if (comment.title != null && comment.content.isNotEmpty()) {
                val headlineTopPx = remember { mutableStateOf<Float?>(null) }

                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
                    Box(
                        modifier = Modifier
                            .width(28.dp)
                            .wrapContentHeight(Alignment.Top)
                            .drawBehind {
                                val cx = size.width / 2f
                                val startY = headlineTopPx.value ?: headerHeight.toPx()
                                val endY = size.height
                                drawLine(
                                    color = SurfaceAlt30,
                                    start = Offset(cx, startY.coerceAtLeast(0f)),
                                    end = Offset(cx, endY),
                                    strokeWidth = 1.dp.toPx()
                                )
                            }
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = comment.title ?: "",
                            modifier = Modifier.onGloballyPositioned { coords ->
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
                            style = TextStyle(fontSize = 15.sp, lineHeight = 20.sp)
                        )
                    }
                }
            } else {
                if (depth > 0) {
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
                        Box(modifier = Modifier.width(28.dp))
                        Spacer(modifier = Modifier.width(8.dp))

                        Column(modifier = Modifier.fillMaxWidth()) {
                            comment.title?.let { t ->
                                Text(
                                    text = t,
                                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                                    style = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 18.sp, lineHeight = 24.sp)
                                )
                            }

                            Text(
                                text = comment.content,
                                color = OnSurfaceVar,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                } else {
                    comment.title?.let { t ->
                        Text(
                            text = t,
                            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                            style = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 18.sp, lineHeight = 24.sp)
                        )
                    }

                    Text(
                        text = comment.content,
                        color = OnSurfaceVar,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            val bg = MaterialTheme.colorScheme.background
            val iconTint = if (bg.luminance() < 0.5f) Color.White else Color.Black
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .height(40.dp)
                    .animateContentSize()
                    .onGloballyPositioned { coords ->
                        actionsTopPx.value = coords.positionInParent().y
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                val topPaddingDp = 20.dp
                val verticalLenDp = topPaddingDp
                val horizontalLenDp = 14.dp
                Box(
                    modifier = Modifier
                        .width(28.dp)
                        .fillMaxHeight()
                        .drawBehind {
                            val cx = size.width / 2f
                            val verticalLen = verticalLenDp.toPx()
                            val horizontalLen = horizontalLenDp.toPx()
                            val maskHalf = 1.dp.toPx()
                            drawRect(
                                color = bg,
                                topLeft = Offset(0f, verticalLen - maskHalf),
                                size = androidx.compose.ui.geometry.Size(size.width, maskHalf * 2f)
                            )

                            if (hasReplies && !expanded) {
                                // Draw only a short horizontal arm at `topPaddingDp` inside the actions column.
                                drawLine(
                                    color = SurfaceAlt30,
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

                if (hasReplies) {
                    val label = run {
                        val c = comment.getDirectReplyCount()
                        if (c == 1) "Show 1 reply" else "Show $c replies"
                    }

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
    
}

