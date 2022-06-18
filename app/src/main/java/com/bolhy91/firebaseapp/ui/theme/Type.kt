package com.bolhy91.firebaseapp.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.bolhy91.firebaseapp.R


val urbanist = FontFamily(
    listOf(
        Font(R.font.urbanist_regular, FontWeight.Normal),
        Font(R.font.urbanist_medium, FontWeight.Medium),
        Font(R.font.urbanist_semibold, FontWeight.SemiBold),
        Font(R.font.urbanist_bold, FontWeight.Bold),
        Font(R.font.urbanist_extrabold, FontWeight.ExtraBold),
        Font(R.font.urbanist_light, FontWeight.Light),
    )
)

// Set of Material typography styles to start with
val Typography = Typography(

    h1 = TextStyle(
        fontFamily = urbanist,
        fontWeight = FontWeight.Bold,
        fontSize = 26.sp
    ),

    h2 = TextStyle(
        fontFamily = urbanist,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp
    ),

    body1 = TextStyle(
        fontFamily = urbanist,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),

    body2 = TextStyle(
        fontFamily = urbanist,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),

    subtitle1 = TextStyle(
        fontFamily = urbanist,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    ),

    subtitle2 = TextStyle(
        fontFamily = urbanist,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp
    ),

    button = TextStyle(
        fontFamily = urbanist,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp
    ),

    caption = TextStyle(
        fontFamily = urbanist,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    )
)