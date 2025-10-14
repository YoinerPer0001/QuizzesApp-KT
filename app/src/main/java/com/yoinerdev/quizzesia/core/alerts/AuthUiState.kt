package com.yoinerdev.quizzesia.core.alerts

import com.yoinerdev.quizzesia.domain.model.User

sealed class AuthUiState {
    object Idle  : AuthUiState()
    object IsLoading : AuthUiState()
    object Success  : AuthUiState()
    object ErrorEmpty  : AuthUiState()
    object ErrorLengthData : AuthUiState()
    object ErrorValidEmail : AuthUiState()
    object ErrorRegister : AuthUiState()
    object ErrorUpdate : AuthUiState()
    object Error : AuthUiState()
    object ErrorAuth : AuthUiState()
    object ErrorAuthGoogle : AuthUiState()
    object ErrorAuthCredential : AuthUiState()
    object SuccessUpdatedAttempts : AuthUiState()
    object ErrorUpdatedAttempts : AuthUiState()
    object AttemptsUserNull : AuthUiState()
    object ErrorInvalidName : AuthUiState()
}
