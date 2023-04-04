package com.example.hiddenghosts.ui.theme

import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color
import com.example.hiddenghosts.ui.theme.GhostColor.PrimaryColor

object GhostColor {
    val PrimaryColor = Color(0xFF025968)
    val DefaultCellColor = Color(0xFF012F36)
    val SuccessColor = Color(0xFF67E65C)
    val PreviewColor = Color(0xFF78E2F3)
    val WrongColor = Color(0xFfFD7777)
    val RestartButtonColor = Color(0xFF026F83)
}
val ColorPalette = lightColors(
    primary = PrimaryColor
)
