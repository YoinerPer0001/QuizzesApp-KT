package com.yoinerdev.quizzesia.core.di

import com.yoinerdev.quizzesia.domain.repository.IAuthRepository
import com.yoinerdev.quizzesia.domain.repository.IQuizzesRepository
import com.yoinerdev.quizzesia.domain.repository.IgeminiRepository
import com.yoinerdev.quizzesia.domain.usecases.GetAllCategoriesUC
import com.yoinerdev.quizzesia.domain.usecases.GetAllLanguagesUC
import com.yoinerdev.quizzesia.domain.usecases.GetPublcQuizzesUC
import com.yoinerdev.quizzesia.domain.usecases.GetUserQuizAttempts
import com.yoinerdev.quizzesia.domain.usecases.GetUserQuizzesUC
import com.yoinerdev.quizzesia.domain.usecases.IgetAllCategories
import com.yoinerdev.quizzesia.domain.usecases.IgetAllLanguages
import com.yoinerdev.quizzesia.domain.usecases.IgetPublcQuizzes
import com.yoinerdev.quizzesia.domain.usecases.IgetUserQuizAttempts
import com.yoinerdev.quizzesia.domain.usecases.IgetUserQuizzes
import com.yoinerdev.quizzesia.domain.usecases.IsaveQuizzInDB
import com.yoinerdev.quizzesia.domain.usecases.IupdateUserAttempts
import com.yoinerdev.quizzesia.domain.usecases.SaveQuizzInDBUC
import com.yoinerdev.quizzesia.domain.usecases.UpdateUserAttemptsUC
import com.yoinerdev.quizzesia.domain.usecases.auth.implementations.SignInWithGoogleUC
import com.yoinerdev.quizzesia.domain.usecases.auth.interfaces.ISignInWithGoogle
import com.yoinerdev.quizzesia.domain.usecases.gemini.CreateQuizGeminiUC
import com.yoinerdev.quizzesia.domain.usecases.gemini.IcreateQuizGemini
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object QuizzesUseCaseProvider {

    @Provides
    @Singleton
    fun provideGetCategoriesUseCase(repository: IQuizzesRepository): IgetAllCategories {
        return GetAllCategoriesUC(repository)
    }

    @Provides
    @Singleton
    fun provideGetLanguagesUseCase(repository: IQuizzesRepository): IgetAllLanguages {
        return GetAllLanguagesUC(repository)
    }

    @Provides
    @Singleton
    fun provideCreateQuizzGeminiUC(repository: IgeminiRepository): IcreateQuizGemini {
        return CreateQuizGeminiUC(repository)
    }

    @Provides
    @Singleton
    fun provideSaveQuizInDBUC(repository: IQuizzesRepository): IsaveQuizzInDB {
        return SaveQuizzInDBUC(repository)
    }

    @Provides
    @Singleton
    fun provideGetAllUserQuizzesUC(repository: IQuizzesRepository): IgetUserQuizzes {
        return GetUserQuizzesUC(repository)
    }

    @Provides
    @Singleton
    fun provideGetAllPublicQuizzesUC(repository: IQuizzesRepository): IgetPublcQuizzes {
        return GetPublcQuizzesUC(repository)
    }

    @Provides
    @Singleton
    fun provideGetUserAttemptsQuizzesUC(repository: IQuizzesRepository): IgetUserQuizAttempts {
        return GetUserQuizAttempts(repository)
    }


}