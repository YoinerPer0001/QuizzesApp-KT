package com.yoinerdev.quizzesia.data.dto

data class CategoriesResponse(
    val code: Int,
    val message: String?,
    val data:List<Category>
)

data class Category(
    val cat_id: String,
    val text: String
)
