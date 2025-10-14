package com.yoinerdev.quizzesia.core.di

import androidx.credentials.CredentialManager
import com.google.firebase.auth.FirebaseAuth
import com.yoinerdev.quizzesia.data.remote.ApiGeminiService
import com.yoinerdev.quizzesia.data.remote.ApiQuizzesService
import com.yoinerdev.quizzesia.data.respository.AuthRepository
import com.yoinerdev.quizzesia.data.respository.GeminiRepository
import com.yoinerdev.quizzesia.data.respository.QuizzesRepository
import com.yoinerdev.quizzesia.data.respository.SocketRepository
import com.yoinerdev.quizzesia.domain.repository.IAuthRepository
import com.yoinerdev.quizzesia.domain.repository.IQuizzesRepository
import com.yoinerdev.quizzesia.domain.repository.ISocketRepository
import com.yoinerdev.quizzesia.domain.repository.IgeminiRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.socket.client.Socket
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryProvider {
    @Provides
    @Singleton
    fun provideAuthRepository(firebaseAuth: FirebaseAuth, authService: ApiQuizzesService, credentialManager:CredentialManager): IAuthRepository {
        return AuthRepository(firebaseAuth, authService, credentialManager)
    }

    @Provides
    @Singleton
    fun provideQuizzesRepository( authService: ApiQuizzesService): IQuizzesRepository {
        return QuizzesRepository(authService)
    }

    @Provides
    @Singleton
    fun provideGeminiRepository( geminiService: ApiGeminiService): IgeminiRepository {
        return GeminiRepository(geminiService)
    }

    @Provides
    @Singleton
    fun provideSocketRepository(
        socket: Socket
    ): ISocketRepository = SocketRepository(socket)
}