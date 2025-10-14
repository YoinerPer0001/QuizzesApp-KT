package com.yoinerdev.quizzesia.core.utils

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream

//convierte uri a file
fun uriToFile(context: Context, uri: Uri, fileName: String = "temp.pdf"): File {
    val file = File(context.cacheDir, fileName)
    context.contentResolver.openInputStream(uri).use { inputStream ->
        FileOutputStream(file).use { outputStream ->
            inputStream?.copyTo(outputStream)
        }
    }
    return file
}