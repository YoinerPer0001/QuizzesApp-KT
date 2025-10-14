package com.yoinerdev.quizzesia.domain.usecases.auth.interfaces

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

interface ILoginWithEmail {
    suspend operator fun invoke(email:String, password:String): Task<AuthResult>?
}