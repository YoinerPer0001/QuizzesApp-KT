package com.yoinerdev.quizzesia.domain.usecases

import com.yoinerdev.quizzesia.domain.model.CategoryMN
import com.yoinerdev.quizzesia.domain.repository.IQuizzesRepository
import javax.inject.Inject

class GetAllCategoriesUC @Inject constructor(
    private val quizzesRepository: IQuizzesRepository
) : IgetAllCategories {
    override suspend fun invoke(lang:String?): List<Pair<String, String>> {
        val list : MutableList<Pair<String, String>> = mutableListOf()
        val result =  quizzesRepository.getAllCategories(lang)
        result.forEach { category ->
            list.add(Pair(category.id, category.text))
        }
        return list
    }
}