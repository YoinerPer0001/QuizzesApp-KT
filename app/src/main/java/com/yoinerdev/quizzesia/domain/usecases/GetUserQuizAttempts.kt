package com.yoinerdev.quizzesia.domain.usecases

import com.yoinerdev.quizzesia.data.dto.Attempt
import com.yoinerdev.quizzesia.domain.repository.IQuizzesRepository
import javax.inject.Inject

class GetUserQuizAttempts @Inject constructor(
    private val quizRepository:IQuizzesRepository
) : IgetUserQuizAttempts {
    override suspend fun invoke(quizId: String): List<Attempt> {
        return quizRepository.getUserAttempts(quizId)
    }
}