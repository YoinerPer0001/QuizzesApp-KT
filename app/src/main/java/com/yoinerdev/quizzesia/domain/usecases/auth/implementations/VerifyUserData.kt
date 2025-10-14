package com.yoinerdev.quizzesia.domain.usecases.auth.implementations

import com.yoinerdev.quizzesia.data.dto.Data
import com.yoinerdev.quizzesia.domain.repository.IAuthRepository
import com.yoinerdev.quizzesia.domain.usecases.auth.interfaces.IVerifyUserData
import javax.inject.Inject


class VerifyUserData @Inject constructor(
    private val repository: IAuthRepository
) : IVerifyUserData {
    override suspend fun invoke(): Data? {
        return repository.VerifyUserData()
    }
}