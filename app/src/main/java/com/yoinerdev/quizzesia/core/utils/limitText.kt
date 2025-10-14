package com.yoinerdev.quizzesia.core.utils

fun limitText(text: String, maxWords: Int): String {
    val words = text.trim().split("\\s+".toRegex())
    return if (words.size <= maxWords) {
        text
    } else {
        words.take(maxWords).joinToString(" ")
    }
}