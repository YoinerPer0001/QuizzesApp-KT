package com.yoinerdev.quizzesia.domain.usecases

import com.yoinerdev.quizzesia.domain.model.CategoryMN

interface IgetAllCategories {
    suspend operator fun invoke(lang:String?): List<Pair<String, String>>
}