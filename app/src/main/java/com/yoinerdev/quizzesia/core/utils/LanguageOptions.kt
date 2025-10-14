package com.yoinerdev.quizzesia.core.utils

import java.util.Locale

val LanguageOptions = listOf(
    "es" to "Español",
    "en" to "Inglés",
    "pt" to "Portugués"
)

val AllLanguageOptions = Locale.getAvailableLocales()
    .map { it.displayName }
    .filter { it.isNotBlank() }
    .distinct()
    .sorted()