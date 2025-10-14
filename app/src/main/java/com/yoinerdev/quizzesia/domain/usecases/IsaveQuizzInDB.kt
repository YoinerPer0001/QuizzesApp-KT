package com.yoinerdev.quizzesia.domain.usecases

import com.yoinerdev.quizzesia.data.dto.CreateQuizResponse
import com.yoinerdev.quizzesia.data.dto.CreateQuizzRequest

interface IsaveQuizzInDB {
    suspend operator fun invoke(data:CreateQuizzRequest): CreateQuizResponse?
}