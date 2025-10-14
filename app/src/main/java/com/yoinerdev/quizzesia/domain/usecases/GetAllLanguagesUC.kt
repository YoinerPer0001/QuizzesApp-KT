package com.yoinerdev.quizzesia.domain.usecases

import com.yoinerdev.quizzesia.domain.repository.IQuizzesRepository
import javax.inject.Inject

class GetAllLanguagesUC @Inject constructor(
    private val quizzesRepository:IQuizzesRepository
) : IgetAllLanguages {
    override suspend fun invoke(): List<Pair<String, String>> {
        val list :MutableList<Pair<String,String>> = mutableListOf()
        val result = quizzesRepository.getAllLanguages()
        result.forEach { lang ->
            list.add(Pair(lang.id, lang.name))
        }
        return list
    }
}