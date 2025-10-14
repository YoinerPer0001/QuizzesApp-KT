package com.yoinerdev.quizzesia.data.remote

import com.yoinerdev.quizzesia.data.dto.GeminiReq
import com.yoinerdev.quizzesia.data.dto.GeminiResp
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiGeminiService {

    @POST("v1beta/models/gemini-2.5-flash-lite:generateContent")
    suspend fun createQuiz(
        @Body data: GeminiReq
    ): Response<GeminiResp>
}