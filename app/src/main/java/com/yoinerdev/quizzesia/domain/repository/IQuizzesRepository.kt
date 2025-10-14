package com.yoinerdev.quizzesia.domain.repository

import com.yoinerdev.quizzesia.data.dto.Attempt
import com.yoinerdev.quizzesia.data.dto.CreateQuizResponse
import com.yoinerdev.quizzesia.data.dto.CreateQuizzRequest
import com.yoinerdev.quizzesia.data.dto.Data
import com.yoinerdev.quizzesia.data.dto.GetAllUserQuizzesResponse
import com.yoinerdev.quizzesia.data.dto.Quiz
import com.yoinerdev.quizzesia.domain.model.CategoryMN
import com.yoinerdev.quizzesia.domain.model.LanguagesMN

interface IQuizzesRepository {
    suspend fun getAllCategories(lang:String?):List<CategoryMN>
    suspend fun getAllLanguages():List<LanguagesMN>
    suspend fun createQuiz(data:CreateQuizzRequest): CreateQuizResponse?
    suspend fun getAllQuizzesUser(page:Int, category:String?): GetAllUserQuizzesResponse?
    suspend fun getAllPublicQuizzes (page:Int, category:String?): GetAllUserQuizzesResponse?
    suspend fun getUserAttempts(quizId:String): List<Attempt>

}