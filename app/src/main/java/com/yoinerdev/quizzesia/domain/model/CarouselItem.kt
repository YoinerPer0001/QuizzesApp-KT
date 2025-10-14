package com.yoinerdev.quizzesia.domain.model

import androidx.annotation.DrawableRes
import androidx.annotation.RawRes

data class CarouselItem(
    val id: Int,
    val title: String,
    val subtitle:String?,
    @RawRes val imageResId: Int,
    val contentDescription: String
)