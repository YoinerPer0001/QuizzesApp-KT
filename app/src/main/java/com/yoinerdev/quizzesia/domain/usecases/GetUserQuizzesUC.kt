package com.yoinerdev.quizzesia.domain.usecases

import com.yoinerdev.quizzesia.data.dto.GetAllUserQuizzesResponse
import com.yoinerdev.quizzesia.data.dto.Quiz
import com.yoinerdev.quizzesia.domain.repository.IQuizzesRepository
import javax.inject.Inject

class GetUserQuizzesUC @Inject constructor(
    private val quizRepository: IQuizzesRepository
) : IgetUserQuizzes {
    override suspend fun invoke(page: Int, categoryId: String?): GetAllUserQuizzesResponse? {
        return quizRepository.getAllQuizzesUser(page, categoryId)
    }

}