package com.yoinerdev.quizzesia.domain.usecases

import com.yoinerdev.quizzesia.data.dto.GetAllUserQuizzesResponse

interface IgetPublcQuizzes {
    suspend operator fun invoke(page:Int, categoryId:String?): GetAllUserQuizzesResponse?
}