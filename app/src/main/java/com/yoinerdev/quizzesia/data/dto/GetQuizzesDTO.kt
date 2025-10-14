package com.yoinerdev.quizzesia.data.dto

data class GetAllUserQuizzesResponse(
    val code: Long,
    val message: String,
    val data: DataQuiz,
)

data class DataQuiz(
    val quizzes: List<Quiz>,
    val total: Long,
    val totalPages: Long,
)

data class Quiz(
    val quiz_id: String,
    val title: String,
    val is_public: Boolean,
    val difficult: String,
    val resources: String,
    val language: Language,
    val category: Category,
    val createdAt:String,
    val attemptCount:String?,
    val creator: Creator?,
    val questionCount: String?,
    val timeQuestion: String?
)

data class Creator(
    val user_id: String,
    val name:String
)