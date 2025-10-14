package com.yoinerdev.quizzesia.domain.repository

import com.yoinerdev.quizzesia.data.dto.GeminiReq
import com.yoinerdev.quizzesia.data.dto.GeminiResp

interface IgeminiRepository {
    suspend fun createQuiz(data:GeminiReq): GeminiResp?
}