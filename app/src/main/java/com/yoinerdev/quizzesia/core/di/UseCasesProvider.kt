package com.yoinerdev.quizzesia.core.di

import com.yoinerdev.quizzesia.domain.repository.IAuthRepository
import com.yoinerdev.quizzesia.domain.repository.IQuizzesRepository
import com.yoinerdev.quizzesia.domain.usecases.GetUserQuizAttempts
import com.yoinerdev.quizzesia.domain.usecases.IgetUserQuizAttempts
import com.yoinerdev.quizzesia.domain.usecases.IupdateUserAttempts
import com.yoinerdev.quizzesia.domain.usecases.UpdateUserAttemptsUC
import com.yoinerdev.quizzesia.domain.usecases.auth.implementations.GetUserSessionUC
import com.yoinerdev.quizzesia.domain.usecases.auth.interfaces.ILoginWithEmail
import com.yoinerdev.quizzesia.domain.usecases.auth.interfaces.IRegisterEmailAndPassword
import com.yoinerdev.quizzesia.domain.usecases.auth.interfaces.ISignInWithGoogle
import com.yoinerdev.quizzesia.domain.usecases.auth.interfaces.ISignOut
import com.yoinerdev.quizzesia.domain.usecases.auth.interfaces.IVerifyUserData
import com.yoinerdev.quizzesia.domain.usecases.auth.implementations.LoginWithEmailUC
import com.yoinerdev.quizzesia.domain.usecases.auth.implementations.RegisterEmailAndPassword
import com.yoinerdev.quizzesia.domain.usecases.auth.implementations.ResetPassword
import com.yoinerdev.quizzesia.domain.usecases.auth.implementations.SignInWithGoogleUC
import com.yoinerdev.quizzesia.domain.usecases.auth.implementations.SignOut
import com.yoinerdev.quizzesia.domain.usecases.auth.implementations.VerifyUserData
import com.yoinerdev.quizzesia.domain.usecases.auth.interfaces.IResetPassword
import com.yoinerdev.quizzesia.domain.usecases.auth.interfaces.IgetUserSessionUC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCasesProvider {

    @Provides
    @Singleton
    fun provideSignInWithGoogleUC(repository: IAuthRepository): ISignInWithGoogle {
        return SignInWithGoogleUC(repository)
    }

    @Provides
    @Singleton
    fun provideVerifyUserData(repository: IAuthRepository): IVerifyUserData {
        return VerifyUserData(repository)
    }

    @Provides
    @Singleton
    fun provideRegisterEmailPass(repository: IAuthRepository): IRegisterEmailAndPassword {
        return RegisterEmailAndPassword(repository)
    }

    @Provides
    @Singleton
    fun provideLoginEmailPassUC(repository: IAuthRepository): ILoginWithEmail {
        return LoginWithEmailUC(repository)
    }

    @Provides
    @Singleton
    fun provideSignOutUC(repository: IAuthRepository): ISignOut {
        return SignOut(repository)
    }

    @Provides
    @Singleton
    fun provideResetPasswordUC(repository: IAuthRepository): IResetPassword {
        return ResetPassword(repository)
    }

    @Provides
    @Singleton
    fun provideGetUserSessionUC(repository: IAuthRepository): IgetUserSessionUC {
        return GetUserSessionUC(repository)
    }

    @Provides
    @Singleton
    fun provideUpdateUserAttemptsUCUC(repository: IAuthRepository): IupdateUserAttempts {
        return UpdateUserAttemptsUC(repository)
    }


}