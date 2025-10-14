package com.yoinerdev.quizzesia.data.mappers

import com.google.firebase.auth.FirebaseUser
import com.yoinerdev.quizzesia.domain.model.User

fun verifyProvider(firebaseUser:FirebaseUser): Boolean{
    val isGoogleProvider = firebaseUser.providerData.any { it.providerId == "google.com" }
    return isGoogleProvider
}

fun FirebaseUser.toUser() : User{
    return User(
        uid = this.uid,
        email = this.email,
        photoUrl = this.photoUrl.toString(),
        displayName = this.displayName,
        isGoogleProvider = verifyProvider(this)
    )
}