package com.yoinerdev.quizzesia.domain.usecases.auth.interfaces

import com.google.firebase.auth.FirebaseUser

interface ISignInWithGoogle {
    suspend operator  fun invoke (idToken:String): FirebaseUser?
}