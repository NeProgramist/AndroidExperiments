package com.np.androidexperiments.presentation.design_system

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontLoadingStrategy
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.Typeface
import androidx.core.graphics.TypefaceCompat
import com.np.androidexperiments.R

val helveticaFamily = FontFamily(
    Font(R.font.helvetica_normal, FontWeight.Normal),
    Font(R.font.helvetica_medium, FontWeight.Medium),
    Font(R.font.helvetica_bold, FontWeight.Bold),
)

val aliceFamily = FontFamily(
    Font(R.font.alice_regular, FontWeight.Normal),
)

val nekstFamily = FontFamily(
    Font(R.font.nekst_bold, FontWeight.Bold),
)

val dyslexicFamily = FontFamily(
    Font(R.font.dysfontpro_regular, FontWeight.Normal),
    Font(R.font.dysfontpro_bold, FontWeight.Bold),
    Font(R.font.dysfontpro_medium, FontWeight.Medium),
)

val manropeFamily = FontFamily(
    Font(R.font.manrope_medium, FontWeight.Medium),
    Font(R.font.manrope_bold, FontWeight.Bold),
    Font(R.font.manrope_regular, FontWeight.Normal),
)
