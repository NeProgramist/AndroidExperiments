package com.np.androidexperiments.presentation.design_system

import android.graphics.Typeface
import android.graphics.fonts.FontFamily
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import com.np.androidexperiments.R

@Immutable
data class Typography(
    val heading1: TextStyle,
    val heading2: TextStyle,
    val body1Bold: TextStyle,
    val body1Medium: TextStyle,
    val body2Bold: TextStyle,
    val body2Medium: TextStyle,
    val body3Regular: TextStyle,
)


val LocalTypography = compositionLocalOf {
    Typography(
        heading1 = TextStyle.Default,
        heading2 = TextStyle.Default,
        body1Bold = TextStyle.Default,
        body1Medium = TextStyle.Default,
        body2Bold = TextStyle.Default,
        body2Medium = TextStyle.Default,
        body3Regular = TextStyle.Default,
    )
}

@Composable
fun AppTheme(
    dyslexicMode: Boolean = false,
//    darkTheme: Boolean = false, to be added in the future
    content: @Composable () -> Unit,
) {
//    val font = android.graphics.fonts.Font.Builder(LocalContext.current.resources, R.font.dysfontpro_bold).build()
//    val fontR = android.graphics.fonts.Font.Builder(LocalContext.current.resources, R.font.manrope_bold).build()
//    val family = FontFamily.Builder(font).build()
//    val familyR = FontFamily.Builder(fontR).build()
//    val typeface = Typeface.CustomFallbackBuilder(family).addCustomFallback(familyR).build()
//    val typefaceC = androidx.compose.ui.text.font.Typeface(typeface)
//    val f = androidx.compose.ui.text.font.FontFamily(typefaceC)

    val t = if (!dyslexicMode) {
        generalTypography
    } else {
//        val variantTypography = Typography(
//            heading1 = heading1Variant.copy(fontFamily = f),
//            heading2 = heading2Variant.copy(fontFamily = f),
//            body1Bold = body1BoldVariant.copy(fontFamily = f),
//            body1Medium = body1MediumVariant.copy(fontFamily = f),
//            body2Bold = body2BoldVariant.copy(fontFamily = f),
//            body2Medium = body2MediumVariant.copy(fontFamily = f),
//            body3Regular = body3Regular.copy(fontFamily = f),
//        )

        variantTypography
        generalTypography
    }


    CompositionLocalProvider(
        LocalTypography provides t,
    ) {
        content()
    }
}

object AppTheme {
    val typography: Typography
        @Composable
        get() = LocalTypography.current
}
