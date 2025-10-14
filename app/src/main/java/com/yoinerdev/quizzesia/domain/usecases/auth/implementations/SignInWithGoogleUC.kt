package com.yoinerdev.quizzesia.domain.usecases.auth.implementations

import com.google.firebase.auth.FirebaseUser
import com.yoinerdev.quizzesia.domain.repository.IAuthRepository
import com.yoinerdev.quizzesia.domain.usecases.auth.interfaces.ISignInWithGoogle
import javax.inject.Inject

class SignInWithGoogleUC @Inject constructor (
    private val repository: IAuthRepository
): ISignInWithGoogle {
    override suspend fun invoke(idToken:String): FirebaseUser? {
        val result = repository.SignInWithGoogle(idToken)
        return result
    }
}