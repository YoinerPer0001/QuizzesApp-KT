package com.yoinerdev.quizzesia.data.respository

import android.util.Log
import com.google.gson.Gson
import com.yoinerdev.quizzesia.data.dto.GeminiReq
import com.yoinerdev.quizzesia.data.dto.GeminiResp
import com.yoinerdev.quizzesia.data.remote.ApiGeminiService
import com.yoinerdev.quizzesia.domain.repository.IgeminiRepository
import javax.inject.Inject

class GeminiRepository @Inject constructor(
    private val geminiService: ApiGeminiService
) : IgeminiRepository {
    override suspend fun createQuiz(data: GeminiReq): GeminiResp? {
        try {
            Log.d("API RESULT", Gson().toJson(data))
            val result = geminiService.createQuiz(data)
            Log.d("API RESULT", result.body().toString())
            if(result.isSuccessful){
                return result.body()
            }else{
                return null
            }

        }catch (e:Exception){
            Log.d("Error to create quizz", e.message.toString())
            return null
        }
    }
}