package com.yoinerdev.quizzesia.domain.usecases.auth.interfaces

import com.yoinerdev.quizzesia.data.dto.Data

interface IVerifyUserData {
    suspend operator fun invoke(): Data?
}