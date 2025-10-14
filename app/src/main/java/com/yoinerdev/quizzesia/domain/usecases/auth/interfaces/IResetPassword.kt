package com.yoinerdev.quizzesia.domain.usecases.auth.interfaces

import com.google.android.gms.tasks.Task


interface IResetPassword {
    suspend operator fun invoke(email:String): Task<Void>?
}