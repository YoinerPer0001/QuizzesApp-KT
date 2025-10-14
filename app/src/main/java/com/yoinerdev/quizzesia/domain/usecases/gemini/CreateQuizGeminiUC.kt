package com.yoinerdev.quizzesia.domain.usecases.gemini

import com.yoinerdev.quizzesia.data.dto.GeminiReq
import com.yoinerdev.quizzesia.data.dto.GeminiResp
import com.yoinerdev.quizzesia.domain.repository.IgeminiRepository
import javax.inject.Inject

class CreateQuizGeminiUC @Inject constructor(
    private val geminiRepo:IgeminiRepository
) : IcreateQuizGemini {
    override suspend fun invoke(data: GeminiReq): GeminiResp? {
        return geminiRepo.createQuiz(data)
    }
}