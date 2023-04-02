package com.example.hiddenghosts.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.hiddenghosts.R

private val InterFontFamily = FontFamily(
    Font(R.font.inter_variable_font),
)

val Typography = Typography(
    defaultFontFamily = InterFontFamily,
    h3 = TextStyle(
        fontWeight = FontWeight.W700,
        fontSize = 32.sp,
        lineHeight = 40.sp
    ),
    body1 = TextStyle(
        fontWeight = FontWeight.W600,
        fontSize = 16.sp,
        lineHeight = 20.sp
    )
)
