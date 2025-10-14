package com.yoinerdev.quizzesia.domain.usecases

import com.yoinerdev.quizzesia.data.dto.GetAllUserQuizzesResponse
import com.yoinerdev.quizzesia.domain.repository.IQuizzesRepository
import javax.inject.Inject

class GetPublcQuizzesUC @Inject constructor(
    private val quizzesRepo : IQuizzesRepository
) : IgetPublcQuizzes {
    override suspend fun invoke(page: Int, categoryId: String?): GetAllUserQuizzesResponse? {
        return quizzesRepo.getAllPublicQuizzes(page, categoryId)
    }

}