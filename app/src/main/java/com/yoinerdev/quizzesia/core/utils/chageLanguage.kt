package com.yoinerdev.quizzesia.core.utils

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import java.util.Locale

@Suppress("DEPRECATION")
fun Context.updateLocale(languageCode: String): Context {
    val locale = Locale(languageCode)
    Locale.setDefault(locale)

    val config = resources.configuration
    config.setLocale(locale)

    return createConfigurationContext(config)
}


