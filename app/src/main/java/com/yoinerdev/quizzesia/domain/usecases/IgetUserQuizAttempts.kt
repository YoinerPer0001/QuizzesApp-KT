package com.yoinerdev.quizzesia.domain.usecases

import com.yoinerdev.quizzesia.data.dto.Attempt

interface IgetUserQuizAttempts {
    suspend operator fun invoke(quizId:String): List<Attempt>
}