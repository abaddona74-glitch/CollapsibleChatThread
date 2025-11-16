package com.maxmudbek.collapsiblechatthread.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.maxmudbek.collapsiblechatthread.ui.theme.OnSurfaceAlt
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.CircleShape
import com.maxmudbek.collapsiblechatthread.ui.theme.ProfileColors
import com.maxmudbek.collapsiblechatthread.ui.theme.Primary

/**
 * Circular profile icon with user's initial
 * Uses color from ProfileColors list based on initial character
 * 
 * @param initial First letter of user's name
 * @param modifier Optional modifier for customization
 */
@Composable
fun ProfileIcon(
    initial: String,
    modifier: Modifier = Modifier
) {
    // Get color based on initial character (consistent color per user)
    val colorIndex = initial.firstOrNull()?.uppercaseChar()?.code?.rem(ProfileColors.size) ?: 0
    // Use specific Primary color for the 'D' initial as requested, otherwise pick from palette
    val backgroundColor = if (initial.equals("D", ignoreCase = true)) Primary else ProfileColors[colorIndex]

    Box(
        modifier = modifier
            .size(28.dp)
            .clip(CircleShape)
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initial.uppercase(),
            color = if (initial.equals("D", ignoreCase = true)) OnSurfaceAlt else Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}
