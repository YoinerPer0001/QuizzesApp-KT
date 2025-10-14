package com.yoinerdev.quizzesia.domain.usecases.auth.interfaces

import com.google.firebase.auth.FirebaseUser

interface IgetUserSessionUC {
    suspend operator fun invoke(): FirebaseUser?
}