package com.yoinerdev.quizzesia.domain.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.yoinerdev.quizzesia.data.dto.Data

interface IAuthRepository {
    suspend fun SignInWithGoogle(idtoken: String): FirebaseUser?
    suspend fun VerifyUserData(): Data?
    suspend fun registerEmailAndPass(email: String, password: String): Task<AuthResult>?
    suspend fun logInWithEmail(email: String, password: String): Task<AuthResult>?
    suspend fun signOut(): Boolean
    suspend fun resetPassword(email:String): Task<Void>?
    suspend fun getUserSession(): FirebaseUser?
    suspend fun updateUserAttempts(data:Data): Boolean?
}