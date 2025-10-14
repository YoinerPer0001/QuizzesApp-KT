package com.yoinerdev.quizzesia.domain.model

import android.net.Uri
import okhttp3.MediaType

data class MediaFile(
    val uri:Uri,
    val name: String,
    val type:MediaType
)
