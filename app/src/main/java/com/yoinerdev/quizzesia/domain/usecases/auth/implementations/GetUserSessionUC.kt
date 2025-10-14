package com.yoinerdev.quizzesia.domain.usecases.auth.implementations

import com.google.firebase.auth.FirebaseUser
import com.yoinerdev.quizzesia.domain.repository.IAuthRepository
import com.yoinerdev.quizzesia.domain.usecases.auth.interfaces.IgetUserSessionUC
import javax.inject.Inject

class GetUserSessionUC @Inject constructor(
    private val authRepo: IAuthRepository
) : IgetUserSessionUC {
    override suspend fun invoke(): FirebaseUser?  {
        return authRepo.getUserSession()
    }

}