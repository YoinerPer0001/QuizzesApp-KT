package com.yoinerdev.quizzesia.domain.usecases

import com.yoinerdev.quizzesia.data.dto.Attempt
import com.yoinerdev.quizzesia.data.dto.Data
import com.yoinerdev.quizzesia.domain.repository.IAuthRepository
import com.yoinerdev.quizzesia.domain.repository.IQuizzesRepository
import javax.inject.Inject

class UpdateUserAttemptsUC @Inject constructor(
    private val authRepo: IAuthRepository
): IupdateUserAttempts {
    override suspend fun invoke(data: Data): Boolean? {
        return authRepo.updateUserAttempts(data)
    }
}