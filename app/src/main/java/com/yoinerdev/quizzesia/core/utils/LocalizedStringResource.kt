package com.yoinerdev.quizzesia.core.utils

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext


@SuppressLint("LocalContextConfigurationRead")
@Composable
fun LocalizedStringResource(@StringRes id: Int): String {
    val context = LocalContext.current
    val locale = LocalAppLocale.current

    val config = Configuration(context.resources.configuration)
    config.setLocale(locale)

    val localizedContext = context.createConfigurationContext(config)
    return localizedContext.getString(id)
}
