package com.yoinerdev.quizzesia.core.helpers

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.Locale

val Context.dataStore by preferencesDataStore("settings")

object PreferencesKeys {
    val APP_LANG = stringPreferencesKey("app_lang")
}

fun getSavedLanguage(context: Context): Flow<String> {
    return context.dataStore.data.map { prefs ->
        prefs[PreferencesKeys.APP_LANG] ?: Locale.getDefault().language
    }
}

fun saveLanguage(context: Context, lang: String) {
    CoroutineScope(Dispatchers.IO).launch {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.APP_LANG] = lang
        }
    }
}