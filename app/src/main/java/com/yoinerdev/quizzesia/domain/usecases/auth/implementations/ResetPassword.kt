package com.yoinerdev.quizzesia.domain.usecases.auth.implementations

import com.google.android.gms.tasks.Task
import com.yoinerdev.quizzesia.domain.repository.IAuthRepository
import com.yoinerdev.quizzesia.domain.usecases.auth.interfaces.IResetPassword
import javax.inject.Inject

class ResetPassword @Inject constructor(
    private val repository: IAuthRepository
) : IResetPassword {
    override suspend fun invoke(email: String): Task<Void>? {
        return repository.resetPassword(email)
    }
}