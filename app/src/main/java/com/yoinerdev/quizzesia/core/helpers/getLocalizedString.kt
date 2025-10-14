package com.yoinerdev.quizzesia.core.helpers

import android.content.Context
import android.content.res.Configuration
import androidx.annotation.StringRes
import java.util.Locale

fun Context.getLocalizedString(@StringRes id: Int, locale: Locale): String {
    val config = Configuration(resources.configuration)
    config.setLocale(locale)
    val localizedContext = createConfigurationContext(config)
    return localizedContext.getString(id)
}
