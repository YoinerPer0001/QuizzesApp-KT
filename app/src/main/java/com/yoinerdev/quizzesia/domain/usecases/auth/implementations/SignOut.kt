package com.yoinerdev.quizzesia.domain.usecases.auth.implementations

import com.yoinerdev.quizzesia.domain.repository.IAuthRepository
import com.yoinerdev.quizzesia.domain.usecases.auth.interfaces.ISignOut
import javax.inject.Inject

class SignOut @Inject constructor(
    private val repository: IAuthRepository
): ISignOut {
    override suspend fun invoke(): Boolean {
        return repository.signOut()
    }
}