package com.yoinerdev.quizzesia.domain.model

data class User(
    val uid: String,
    var displayName: String?,
    val email: String?,
    val photoUrl: String?,
    val isGoogleProvider: Boolean
)
