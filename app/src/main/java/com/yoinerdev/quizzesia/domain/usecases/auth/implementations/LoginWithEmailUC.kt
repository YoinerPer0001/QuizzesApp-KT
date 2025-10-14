package com.yoinerdev.quizzesia.domain.usecases.auth.implementations

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.yoinerdev.quizzesia.domain.repository.IAuthRepository
import com.yoinerdev.quizzesia.domain.usecases.auth.interfaces.ILoginWithEmail
import javax.inject.Inject

class LoginWithEmailUC @Inject constructor(
    private val repository:IAuthRepository
) : ILoginWithEmail {
    override suspend fun invoke(email: String, password: String): Task<AuthResult>? {
        val result = repository.logInWithEmail(email, password)
        return result
    }
}