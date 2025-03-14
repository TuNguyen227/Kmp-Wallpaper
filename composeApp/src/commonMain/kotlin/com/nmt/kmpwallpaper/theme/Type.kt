package com.nmt.kmpwallpaper.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.nmt.kmpwallpaper.composeApp.commonMain.Res
import com.nmt.kmpwallpaper.composeApp.commonMain.poppins_bold
import com.nmt.kmpwallpaper.composeApp.commonMain.poppins_light
import com.nmt.kmpwallpaper.composeApp.commonMain.poppins_semibold
import org.jetbrains.compose.resources.Font

@Composable
fun getTypography() : Typography {
    val poppinsLightFontFamily = FontFamily(
        Font(
            Res.font.poppins_light,
            FontWeight.Light,
            FontStyle.Normal
        )
    )

    val poppinsSemiBoldFontFamily = FontFamily(
        Font(
            Res.font.poppins_semibold,
            FontWeight.SemiBold,
            FontStyle.Normal
        )
    )

    val poppinsBoldFontFamily = FontFamily(
        Font(
            Res.font.poppins_bold,
            FontWeight.Bold,
            FontStyle.Normal
        )
    )
    return Typography(
        bodyLarge =
        TextStyle(
            fontFamily = poppinsSemiBoldFontFamily,
            fontSize = 22.sp,
            lineHeight = 32.sp,
            letterSpacing = 0.5.sp,
        ),
        titleLarge =
        TextStyle(
            fontFamily = poppinsBoldFontFamily,
            fontSize = 28.sp,
            lineHeight = 38.sp,
            letterSpacing = 0.sp,
        ),
        bodyMedium =
        TextStyle(
            fontFamily = poppinsLightFontFamily,
            fontSize = 14.sp,
            lineHeight = 22.sp,
            letterSpacing = 0.5.sp,
        ),
    )
}
