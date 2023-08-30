package com.np.androidexperiments.presentation.design_system

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val heading1 = TextStyle(
    color = black,
    fontSize = 50.sp,
    lineHeight = 50.sp,
    fontFamily = manropeFamily,
    fontWeight = FontWeight.W700,
)

val heading2 = TextStyle(
    color = black,
    fontSize = 40.sp,
    lineHeight = 40.sp,
    fontFamily = manropeFamily,
    fontWeight = FontWeight.W700,
)


val body1Bold = TextStyle(
    color = black,
    fontSize = 18.sp,
    lineHeight = 26.sp,
    fontFamily = manropeFamily,
    fontWeight = FontWeight.W700,
)

val body1Medium = TextStyle(
    color = black,
    fontSize = 18.sp,
    lineHeight = 26.sp,
    fontFamily = manropeFamily,
    fontWeight = FontWeight.W500,
)

val body2Bold = TextStyle(
    color = black,
    fontSize = 15.sp,
    lineHeight = 22.sp,
    fontFamily = manropeFamily,
    fontWeight = FontWeight.W700,
)

val body2Medium = TextStyle(
    color = black,
    fontSize = 15.sp,
    lineHeight = 22.sp,
    fontFamily = manropeFamily,
    fontWeight = FontWeight.W500,
)

val body3Regular = TextStyle(
    color = black,
    fontSize = 13.sp,
    lineHeight = 18.sp,
    fontFamily = manropeFamily,
    fontWeight = FontWeight.W400,
)

val heading1Variant = TextStyle(
    color = black,
    fontSize = 50.sp,
    lineHeight = 50.sp,
    fontFamily = dyslexicFamily,
    fontWeight = FontWeight.W700,
)

val heading2Variant = TextStyle(
    color = black,
    fontSize = 40.sp,
    lineHeight = 40.sp,
    fontFamily = dyslexicFamily,
    fontWeight = FontWeight.W700,
)

val body1BoldVariant = TextStyle(
    color = black,
    fontSize = 18.sp,
    lineHeight = 26.sp,
    fontFamily = dyslexicFamily,
    fontWeight = FontWeight.W700,
)

val body1MediumVariant = TextStyle(
    color = black,
    fontSize = 18.sp,
    lineHeight = 26.sp,
    fontFamily = dyslexicFamily,
    fontWeight = FontWeight.W500,
)

val body2BoldVariant = TextStyle(
    color = black,
    fontSize = 15.sp,
    lineHeight = 22.sp,
    fontFamily = dyslexicFamily,
    fontWeight = FontWeight.W700,
)

val body2MediumVariant = TextStyle(
    color = black,
    fontSize = 15.sp,
    lineHeight = 22.sp,
    fontFamily = dyslexicFamily,
    fontWeight = FontWeight.W500,
)

val generalTypography = Typography(
    heading1 = heading1,
    heading2 = heading2,
    body1Bold = body1Bold,
    body1Medium = body1Medium,
    body2Bold = body2Bold,
    body2Medium = body2Medium,
    body3Regular = body3Regular,
)

val variantTypography = Typography(
    heading1 = heading1Variant,
    heading2 = heading2Variant,
    body1Bold = body1BoldVariant,
    body1Medium = body1MediumVariant,
    body2Bold = body2BoldVariant,
    body2Medium = body2MediumVariant,
    body3Regular = body3Regular,
)
