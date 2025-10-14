package com.yoinerdev.quizzesia.domain.usecases.auth.implementations

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.yoinerdev.quizzesia.domain.repository.IAuthRepository
import com.yoinerdev.quizzesia.domain.usecases.auth.interfaces.IRegisterEmailAndPassword
import javax.inject.Inject

class RegisterEmailAndPassword @Inject constructor(
    private val repository: IAuthRepository
) : IRegisterEmailAndPassword {
    override suspend fun invoke(email:String, password:String): Task<AuthResult>? {
        return repository.registerEmailAndPass(email, password)
    }
}