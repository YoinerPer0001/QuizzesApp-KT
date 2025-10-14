package com.yoinerdev.quizzesia.domain.usecases.auth.interfaces

interface ISignOut {
    suspend operator fun invoke(): Boolean
}