package com.yoinerdev.quizzesia.data.mappers

import com.yoinerdev.quizzesia.data.dto.Category
import com.yoinerdev.quizzesia.domain.model.CategoryMN

fun Category.toCategoryMN(): CategoryMN {
    return CategoryMN(
        id = this.cat_id,
        text = this.text
    )
}

fun CategoryMN.toCategory(): Category {
    return Category(
        cat_id = this.id,
        text = this.text
    )
}