package com.solutionium.sharedui.designsystem.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.solutionium.core.ui.common.R

actual object SharedUiFonts {
    actual val primary: FontFamily = FontFamily(
        Font(R.font.vazir_thin, FontWeight.Thin),
        Font(R.font.vazir_light, FontWeight.ExtraLight),
        Font(R.font.vazir_light, FontWeight.Light),
        Font(R.font.vazir_regular, FontWeight.Normal),
        Font(R.font.vazir_medium, FontWeight.Medium),
        Font(R.font.vazir_bold, FontWeight.SemiBold),
        Font(R.font.vazir_bold, FontWeight.Bold),
        Font(R.font.vazir_bold, FontWeight.ExtraBold),
        Font(R.font.vazir_black, FontWeight.Black)
    )
}
