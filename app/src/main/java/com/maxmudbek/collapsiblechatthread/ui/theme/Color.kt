package com.maxmudbek.collapsiblechatthread.ui.theme

import androidx.compose.ui.graphics.Color

// App color tokens (from Figma)
val DarkBackground = Color(0xFF0F1318) // Primary dark background
val DarkBackgroundAlt = Color(0xFF06060A)

// Surface with 50% emphasis
val Surface50 = Color(0xFF1F222A)

// Text / on-surface
val OnSurface = Color(0xFFFFFFFF)
val OnSurfaceVar = Color(0xFFAFB2B9)
val OnSurfaceAlt = Color(0xFF0F0F18)

// Surface alt with 30% white overlay
val SurfaceAlt30 = Color(0x4DFFFFFF)

// Accent / primary
val Primary = Color(0xFF75FABF)

// Yellow variants
val Yellow = Color(0xFFFFC368)
val Yellow12 = Color(0x1FFFC368) // 12% alpha

// Blue variants
val Blue = Color(0xFF68C3FF)
val Blue12 = Color(0x1168C3FF) // 12% alpha

// Tag/Badge colors (fallbacks)
val TagBackground = Color(0xFF2A2A2A)
val TagBorder = Color(0xFF404040)

// Connector line color (divider) — White at 30% alpha
val ConnectorLine = Color(0x4DFFFFFF)

// Backwards-compatible aliases (used throughout the codebase)
val AccentBlue = Primary
val AccentPurple = Color(0xFF9B59B6)
val DarkSurface = Surface50
val DarkSurfaceVariant = Color(0xFF2C2C2C)
val TextPrimary = OnSurface
val TextSecondary = OnSurfaceVar
val TextTertiary = Color(0xFF808080)

// Profile icon backgrounds (variety for different users)
val ProfileColors = listOf(
    Color(0xFF6366F1), // Indigo
    Color(0xFFEC4899), // Pink
    Color(0xFF10B981), // Green
    Color(0xFFF59E0B), // Amber
    Color(0xFF8B5CF6), // Purple
    Color(0xFF06B6D4), // Cyan
    Color(0xFFEF4444), // Red
    Color(0xFF3B82F6), // Blue
)