package com.maxmudbek.collapsiblechatthread.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.maxmudbek.collapsiblechatthread.ui.theme.TagBackground
import com.maxmudbek.collapsiblechatthread.ui.theme.TagBorder
import com.maxmudbek.collapsiblechatthread.ui.theme.TextSecondary

/**
 * Small badge/tag component for user achievements
 * Examples: "Top 1% Commenter", "Top 1% Poster"
 * 
 * @param text Tag text to display
 * @param modifier Optional modifier for customization
 */
@Composable
fun TagChip(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        modifier = modifier
            .background(
                color = TagBackground,
                shape = RoundedCornerShape(4.dp)
            )
            .border(
                width = 1.dp,
                color = TagBorder,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(horizontal = 8.dp, vertical = 4.dp),
        color = TextSecondary,
        fontSize = 11.sp
    )
}
