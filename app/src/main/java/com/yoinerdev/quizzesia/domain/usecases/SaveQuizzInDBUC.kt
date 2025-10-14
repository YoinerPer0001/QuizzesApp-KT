package com.yoinerdev.quizzesia.domain.usecases

import com.yoinerdev.quizzesia.data.dto.CreateQuizResponse
import com.yoinerdev.quizzesia.data.dto.CreateQuizzRequest
import com.yoinerdev.quizzesia.domain.repository.IQuizzesRepository
import javax.inject.Inject

class SaveQuizzInDBUC @Inject constructor(
    private val quizzRepository: IQuizzesRepository
) : IsaveQuizzInDB {
    override suspend fun invoke(data: CreateQuizzRequest): CreateQuizResponse? {
        return quizzRepository.createQuiz(data = data)
    }
}