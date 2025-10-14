package com.yoinerdev.quizzesia.core.utils

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

fun formatDate(isoDate: String): String {
    // formato de entrada ISO 8601 (el que te llega del backend)
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    inputFormat.timeZone = TimeZone.getTimeZone("UTC")

    // formato de salida (el que mostrarás al usuario)
    val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale("es", "ES"))

    val date = inputFormat.parse(isoDate)
    return outputFormat.format(date!!)
}
