package com.yoinerdev.quizzesia.presentation.viewmodels

import android.annotation.SuppressLint
import android.util.Log
import android.util.Patterns
import androidx.credentials.Credential
import androidx.credentials.CustomCredential
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.firebase.auth.userProfileChangeRequest
import com.yoinerdev.quizzesia.core.alerts.AuthUiState
import com.yoinerdev.quizzesia.data.mappers.toUser
import com.yoinerdev.quizzesia.domain.model.User
import com.yoinerdev.quizzesia.domain.usecases.auth.interfaces.IRegisterEmailAndPassword
import com.yoinerdev.quizzesia.domain.usecases.auth.interfaces.ISignInWithGoogle
import com.yoinerdev.quizzesia.domain.usecases.auth.interfaces.IVerifyUserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import com.yoinerdev.quizzesia.data.dto.Data
import com.yoinerdev.quizzesia.domain.usecases.IupdateUserAttempts
import com.yoinerdev.quizzesia.domain.usecases.auth.interfaces.ILoginWithEmail
import com.yoinerdev.quizzesia.domain.usecases.auth.interfaces.IResetPassword
import com.yoinerdev.quizzesia.domain.usecases.auth.interfaces.ISignOut
import com.yoinerdev.quizzesia.domain.usecases.auth.interfaces.IgetUserSessionUC
import kotlinx.coroutines.delay

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signInWithGoogleUC: ISignInWithGoogle,
    private val verifyUserData: IVerifyUserData,
    private val registerEmailAndPassword: IRegisterEmailAndPassword,
    private val loginWithEmailUC: ILoginWithEmail,
    private val signOutUC: ISignOut,
    private val resetPasswordUC: IResetPassword,
    private val getUserSessionUC: IgetUserSessionUC,
    private val updateUserAttemptUC:IupdateUserAttempts
) : ViewModel() {

    private val user_ = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = user_

    private val attempsRemaining_ = MutableStateFlow<Data?>(null)
    val attempsRemaining: StateFlow<Data?> = attempsRemaining_

    private val state_ = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val state: StateFlow<AuthUiState> = state_

    private val logged_ = MutableStateFlow<Boolean>(false)
    val logged: StateFlow<Boolean> = logged_


    //login with google
    fun handleSignIn(credential: Credential) {
        state_.value = AuthUiState.IsLoading
        viewModelScope.launch {
            try {
                // Check if credential is of type Google ID
                if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    // Create Google ID Token
                    val googleIdTokenCredential =
                        GoogleIdTokenCredential.createFrom(credential.data)

                    val idToken = googleIdTokenCredential.idToken

                    val firebaseUser = signInWithGoogleUC(idToken)
                    val token = firebaseUser?.getIdToken(true)
                        ?.await()?.token //token para enviar al backend
                    Log.d("EL TOKEN", token.toString())
                    getuserAttempts() //confirm in backend


                    if (firebaseUser != null) {
                        user_.value = firebaseUser.toUser()
                        state_.value = AuthUiState.Success

                    } else {
                        state_.value = AuthUiState.ErrorAuthGoogle
                    }


                } else {
                    state_.value = AuthUiState.ErrorAuthCredential
                }
            } catch (e: Exception) {
                state_.value = AuthUiState.Error
            }

        }

    }

    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                state_.value = AuthUiState.IsLoading

                if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    state_.value = AuthUiState.ErrorEmpty
                    return@launch
                } else if (!name.matches(Regex("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$"))) {
                    // Solo letras y espacios (permite acentos y ñ)
                    state_.value = AuthUiState.ErrorInvalidName
                    return@launch
                } else if (password.length < 8) {
                    state_.value = AuthUiState.ErrorLengthData
                    return@launch
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    state_.value = AuthUiState.ErrorValidEmail
                    return@launch
                }

                val authResult = registerEmailAndPassword(email, password)
                authResult?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        user_.value = task.result.user?.toUser()

                        val profileUpdates = userProfileChangeRequest {
                            displayName = name
                        }



                        task.result.user?.updateProfile(profileUpdates)
                            ?.addOnCompleteListener { taskUpd ->
                                if (taskUpd.isSuccessful) {
                                    user_.value?.displayName = name



                                    task.result.user?.getIdToken(true)
                                        ?.addOnCompleteListener { resultTask -> //forzar renovacion del token
                                            if(resultTask.isSuccessful){
                                                viewModelScope.launch {
                                                    val result = verifyUserData()
                                                    attempsRemaining_.value = result
                                                    state_.value = AuthUiState.Success
                                                }
                                            }else{
                                                state_.value = AuthUiState.ErrorUpdate
                                            }
                                        }
                                } else {
                                    // Perfil no actualizado, pero el usuario sí existe
                                    state_.value = AuthUiState.ErrorUpdate
                                }
                            }

                    } else {
                        state_.value = AuthUiState.ErrorRegister
                    }
                }

            } catch (e: Exception) {
                state_.value = AuthUiState.ErrorRegister
            } finally {
                delay(50)
                state_.value = AuthUiState.Idle
            }
        }
    }

    fun loginWithEmail(email: String, password: String) {
        viewModelScope.launch {
            state_.value = AuthUiState.IsLoading
            try {
                if (email.isEmpty() || password.isEmpty()) {
                    state_.value = AuthUiState.ErrorEmpty
                    return@launch
                }

                val authResult = loginWithEmailUC(email, password)
                authResult?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        user_.value = task.result.user?.toUser()
                        state_.value = AuthUiState.Success
                        Log.d("USERDATA", user_.value.toString())
                    } else {
                        state_.value = AuthUiState.ErrorAuth
                    }
                }
            } catch (e: Exception) {
                state_.value = AuthUiState.Error
            } finally {
                delay(50)
                state_.value = AuthUiState.Idle
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            try {
                val result = signOutUC()
                user_.emit(null)
                delay(50)
                state_.value = AuthUiState.Success
            } catch (e: Exception) {
                state_.value = AuthUiState.Error
            } finally {
                delay(50)
                state_.value = AuthUiState.Idle
            }
        }
    }

    fun resetPassword(email: String, response: (Boolean) -> Unit) {
        viewModelScope.launch {
            state_.value = AuthUiState.IsLoading
            try {
                if (email.isEmpty()) {
                    state_.value = AuthUiState.ErrorEmpty
                    return@launch
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    state_.value = AuthUiState.ErrorValidEmail
                    return@launch
                }

                val result = resetPasswordUC(email)
                result?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        response(true)
                    } else {
                        response(false)
                    }
                }
            } catch (e: Exception) {
                state_.value = AuthUiState.Error
            } finally {
                delay(50)
                state_.value = AuthUiState.Idle
            }
        }
    }

    fun getUserSesion() {
        viewModelScope.launch {
            try {
                state_.value = AuthUiState.IsLoading
                val isUserLogued = getUserSessionUC()
                if (isUserLogued != null) {
                    getuserAttempts()
                    user_.emit(isUserLogued.toUser())
                    logged_.emit(true)
                    state_.value = AuthUiState.Success
                } else {
                    state_.value = AuthUiState.Idle
                    logged_.emit(false)
                }

            } catch (e: Exception) {
                logged_.emit(false)
                state_.value = AuthUiState.Error
            } finally {
                delay(50)
                state_.value = AuthUiState.Idle
            }
        }
    }

    fun getuserAttempts() {
        viewModelScope.launch {
            try {
                val result = verifyUserData()
                Log.d("RESULT ATTEPMT", result.toString())
                attempsRemaining_.emit(result)
            } catch (e: Exception) {
                delay(50)
                state_.value = AuthUiState.Error
            }
        }
    }

    fun updateUserAttempts(){
        viewModelScope.launch {
            try {
                state_.value = AuthUiState.IsLoading
                if(attempsRemaining_.value != null){
                    val attemptsNew = attempsRemaining_.value?.attempts_remaining?.plus(1)
                    val data = Data(attempts_remaining = attemptsNew ?: 1)
                    val result = updateUserAttemptUC(data)
                    if(result != null){
                        if(result){
                            getuserAttempts()
                            state_.emit(AuthUiState.SuccessUpdatedAttempts)
                        }else{
                            state_.emit(AuthUiState.ErrorUpdatedAttempts)
                        }
                    }else{
                        state_.emit(AuthUiState.ErrorUpdatedAttempts)
                    }
                }else{
                    state_.emit(AuthUiState.AttemptsUserNull)
                }

            } catch (e: Exception) {
                state_.value = AuthUiState.Error
            } finally {
                delay(50)
                state_.value = AuthUiState.Idle
            }
        }
    }

}
