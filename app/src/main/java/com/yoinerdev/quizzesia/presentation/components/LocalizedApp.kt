package com.yoinerdev.quizzesia.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.yoinerdev.quizzesia.core.utils.updateLocale

@Composable
fun LocalizedApp(languageCode: String, content: @Composable () -> Unit) {
    val context = LocalContext.current
    val localizedContext = remember(languageCode) {
        context.updateLocale(languageCode)
    }

    CompositionLocalProvider(
        LocalContext provides localizedContext
    ) {
        content()
    }
}