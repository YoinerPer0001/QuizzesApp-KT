package com.yoinerdev.quizzesia.data.remote


import com.yoinerdev.quizzesia.data.dto.AttemptResponse
import com.yoinerdev.quizzesia.data.dto.AuthResponse
import com.yoinerdev.quizzesia.data.dto.CategoriesResponse
import com.yoinerdev.quizzesia.data.dto.CreateQuizResponse
import com.yoinerdev.quizzesia.data.dto.CreateQuizzRequest
import com.yoinerdev.quizzesia.data.dto.Data
import com.yoinerdev.quizzesia.data.dto.GetAllUserQuizzesResponse
import com.yoinerdev.quizzesia.data.dto.LanguagesResponse
import com.yoinerdev.quizzesia.data.dto.UpdateAttemptsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ApiQuizzesService {
    @POST("login")
    suspend fun login(): Response<AuthResponse>

    @GET("categories/all")
    suspend fun getAllQuizzesCategories(
        @Query("lang") codeLang:String? = null
    ): Response<CategoriesResponse>

    @GET("languages/all")
    suspend fun getAllLanguages():Response<LanguagesResponse>

    @POST("quizzes/create")
    suspend fun createQuiz(
        @Body data:CreateQuizzRequest
    ): Response<CreateQuizResponse>

    @GET("quizzes/all/user")
    suspend fun getUserQuizzes(
        @Query("page") page: Int,
        @Query("category") category:String? = null
    ): Response<GetAllUserQuizzesResponse>

    @GET("quizzes/all/public")
    suspend fun getPublicQuizzes(
        @Query("page") page: Int,
        @Query("category") category:String? = null
    ): Response<GetAllUserQuizzesResponse>

    @GET("attempts/user")
    suspend fun getUserAttempts(
        @Query("quiz_id") quizId:String
    ): Response<AttemptResponse>

    @PUT("user/update")
    suspend fun updateUserAttempts(
        @Body data:Data
    ):Response<UpdateAttemptsResponse>
}