package com.yoinerdev.quizzesia.data.dto

data class AttemptResponse(
    val code: Long,
    val message: String,
    val data: List<Attempt>,
)

data class Attempt(
    val attempt_id: String,
    val score: Long,
    val createdAt: String,
)
