package com.yoinerdev.quizzesia.data.dto

data class LanguagesResponse(
    val code: Int,
    val message: String?,
    val data:List<Language>
)

data class Language(
    val language_id: String,
    val name: String,
    val nativeName:String
)
