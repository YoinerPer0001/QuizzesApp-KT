package com.yoinerdev.quizzesia.data.respository

import android.util.Log
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.yoinerdev.quizzesia.data.remote.ApiQuizzesService
import com.yoinerdev.quizzesia.data.dto.Data
import com.yoinerdev.quizzesia.domain.repository.IAuthRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val authService: ApiQuizzesService,
    private val credentialManager: CredentialManager
) : IAuthRepository {


    override suspend fun SignInWithGoogle(idtoken: String): FirebaseUser? {

        val credential = GoogleAuthProvider.getCredential(idtoken, null)

        return try {
            val result = firebaseAuth.signInWithCredential(credential).await()
            result.user
        }catch (e:Exception){
            Log.e("AuthRepository", "signInWithGoogle:failure", e)
            null
        }
    }

    override suspend fun VerifyUserData(): Data? {
        try {

            val result = authService.login()
            Log.d("RESULT BACK", result.body().toString())
            if(result.isSuccessful){
                return result.body()?.data
            }else{
                return null
            }

        }catch (e:Exception){
            Log.e("VerifyUserData", "signInWithGoogle:failure", e)
            return null
        }
    }

    override suspend fun registerEmailAndPass(email: String, password: String): Task<AuthResult>? {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password)
            return result
        }catch (e:Exception){
            Log.e("registerEmailAndPass", "registerEmailAndPass:failure", e)
            null
        }
    }

    override suspend fun logInWithEmail(email: String, password: String): Task<AuthResult>? {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password)
            return result
        }catch (e:Exception){
            Log.e("logInWithEmail", "logInWithEmail:failure", e)
            null
        }
    }

    override suspend fun signOut(): Boolean {
        try {
            firebaseAuth.signOut()
            credentialManager.clearCredentialState(ClearCredentialStateRequest())
            return true
        }catch (e:Exception){
            Log.e("signOut", "signOut:failure", e)
            return false
        }
    }

    override suspend fun resetPassword(email: String): Task<Void>? {
        try {
            val result = firebaseAuth.sendPasswordResetEmail(email)
            return result
        }catch (e:Exception){
            Log.e("resetPassword", "resetPassword:failure", e)
            return null
        }
    }

    override suspend fun getUserSession(): FirebaseUser? {
        try {
            val user = firebaseAuth.currentUser

            return user

        }catch (e:Exception){
            Log.e("getUserSession", "getUserSession:failure", e)
            return null
        }
    }

    override suspend fun updateUserAttempts(data: Data): Boolean? {
        try {
            val result = authService.updateUserAttempts(data)
            return if (result.body()?.data?.toInt() == 1) {
                true
            } else {
                false
            }
        } catch (e: Exception) {
            Log.d("Error to update Attempts", e.message.toString())
            return null
        }
    }

}