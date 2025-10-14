package com.yoinerdev.quizzesia.data.dto

data class AuthResponse(
    val code: Long,
    val message: String,
    val data: Data,
)

data class Data(
    var attempts_remaining: Long,
)

data class UpdateAttemptsResponse(
    val code: Long,
    val message: String,
    val data: Long,
)