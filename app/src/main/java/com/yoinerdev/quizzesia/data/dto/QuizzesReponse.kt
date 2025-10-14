package com.yoinerdev.quizzesia.data.dto

data class CreateQuizzRequest(
    val title:String,
    val language_id:String,
    val is_public:Boolean,
    val difficult:String,
    val category_id:String,
    val resources:String,
    val data:List<Questions>
)

data class Questions(
    val question: Question,
)

data class Question(
    val text: String,
    val time_limit: Long,
    val type: String,
    val answers: List<Answer>,
)

data class Answer(
    val answer_id: String,
    val text: String,
    val is_correct: Boolean,
)

data class CreateQuizResponse(
    val code: Long,
    val message: String,
    val data: AttemptRemaining?,
)

data class AttemptRemaining(
    val quiz_id: String,
    val attemptsRemaining: Long,
)

