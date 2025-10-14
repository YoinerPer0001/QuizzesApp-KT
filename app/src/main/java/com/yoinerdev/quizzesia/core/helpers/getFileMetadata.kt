package com.yoinerdev.quizzesia.core.helpers

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns

fun Context.getFileMetaData(uri: Uri): Pair<String, Long>? {
    val projection = arrayOf(
        OpenableColumns.DISPLAY_NAME,
        OpenableColumns.SIZE
    )
    contentResolver.query(uri, projection, null, null, null)?.use { cursor ->
        val nameIndex = cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME)
        val sizeIndex = cursor.getColumnIndexOrThrow(OpenableColumns.SIZE)
        if (cursor.moveToFirst()) {
            val name = cursor.getString(nameIndex)      // Nombre del archivo
            val size = cursor.getLong(sizeIndex)        // Tamaño en bytes
            return name to size
        }
    }
    return null
}
