package com.yoinerdev.quizzesia.data.respository

import android.util.Log
import com.google.gson.Gson
import com.yoinerdev.quizzesia.data.dto.Attempt
import com.yoinerdev.quizzesia.data.dto.CreateQuizResponse
import com.yoinerdev.quizzesia.data.dto.CreateQuizzRequest
import com.yoinerdev.quizzesia.data.dto.Data
import com.yoinerdev.quizzesia.data.dto.GetAllUserQuizzesResponse
import com.yoinerdev.quizzesia.data.dto.Quiz
import com.yoinerdev.quizzesia.data.mappers.toCategoryMN
import com.yoinerdev.quizzesia.data.mappers.toLanguagesMN
import com.yoinerdev.quizzesia.data.remote.ApiQuizzesService
import com.yoinerdev.quizzesia.domain.model.CategoryMN
import com.yoinerdev.quizzesia.domain.model.LanguagesMN
import com.yoinerdev.quizzesia.domain.repository.IQuizzesRepository
import javax.inject.Inject

class QuizzesRepository @Inject constructor(
    private val ApiService: ApiQuizzesService
) : IQuizzesRepository {
    override suspend fun getAllCategories(lang: String?): List<CategoryMN> {
        try {
            val list: MutableList<CategoryMN> = mutableListOf()
            val result = ApiService.getAllQuizzesCategories(lang)
            result.body()?.data?.forEach { cat ->
                list.add(cat.toCategoryMN())
            }
            return list
        } catch (e: Exception) {
            Log.d("Error get all categories", e.message.toString())
            return emptyList()
        }
    }

    override suspend fun getAllLanguages(): List<LanguagesMN> {
        try {
            val list: MutableList<LanguagesMN> = mutableListOf()
            val result = ApiService.getAllLanguages()
            result.body()?.data?.forEach { cat ->
                list.add(cat.toLanguagesMN())
            }

            return list
        } catch (e: Exception) {
            Log.d("Error get all Languages", e.message.toString())
            return emptyList()
        }
    }

    override suspend fun createQuiz(data: CreateQuizzRequest): CreateQuizResponse? {
        try {

            val result = ApiService.createQuiz(data)
            return result.body()

        } catch (e: Exception) {
            Log.d("Error to createQuiz", e.message.toString())
            return null
        }
    }

    override suspend fun getAllQuizzesUser(
        page: Int,
        category: String?
    ): GetAllUserQuizzesResponse? {
        try {

            val result = ApiService.getUserQuizzes(page, category)
            return result.body()

        } catch (e: Exception) {
            Log.d("Error to createQuiz", e.message.toString())
            return null
        }
    }

    override suspend fun getAllPublicQuizzes(
        page: Int,
        category: String?
    ): GetAllUserQuizzesResponse? {
        try {
            val result = ApiService.getPublicQuizzes(page, category)
            return result.body()
        } catch (e: Exception) {
            Log.d("Error to createQuiz", e.message.toString())
            return null
        }
    }

    override suspend fun getUserAttempts(quizId: String): List<Attempt> {
        try {
            val result = ApiService.getUserAttempts(quizId)
            return result.body()?.data ?: emptyList()
        } catch (e: Exception) {
            Log.d("Error to get Attempts", e.message.toString())
            return emptyList()
        }
    }


}