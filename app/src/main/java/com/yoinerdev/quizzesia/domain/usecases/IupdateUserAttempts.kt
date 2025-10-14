package com.yoinerdev.quizzesia.domain.usecases

import com.yoinerdev.quizzesia.data.dto.Data

interface IupdateUserAttempts {
    suspend operator fun invoke(data:Data):Boolean?
}