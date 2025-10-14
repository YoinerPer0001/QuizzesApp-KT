package com.yoinerdev.quizzesia.domain.usecases.gemini

import com.yoinerdev.quizzesia.data.dto.GeminiReq
import com.yoinerdev.quizzesia.data.dto.GeminiResp

interface IcreateQuizGemini {
    suspend operator fun invoke(data:GeminiReq): GeminiResp?
}