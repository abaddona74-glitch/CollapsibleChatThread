package com.maxmudbek.collapsiblechatthread.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.maxmudbek.collapsiblechatthread.R
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp


val Typography = Typography(
    bodyLarge = TextStyle(
        
        fontFamily = FontFamily(
            Font(R.font.urbanist_regular, FontWeight.Normal),
            Font(R.font.urbanist_semi_bold, FontWeight.SemiBold),
            Font(R.font.urbanist_medium, FontWeight.Medium),
            Font(R.font.urbanist_bold, FontWeight.Bold)
        ),
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.01.em
    )
    
)