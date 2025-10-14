package com.yoinerdev.quizzesia.domain.usecases

import com.yoinerdev.quizzesia.data.dto.GetAllUserQuizzesResponse
import com.yoinerdev.quizzesia.data.dto.Quiz

interface IgetUserQuizzes {
    suspend operator fun invoke(page:Int, categoryId:String?): GetAllUserQuizzesResponse?
}