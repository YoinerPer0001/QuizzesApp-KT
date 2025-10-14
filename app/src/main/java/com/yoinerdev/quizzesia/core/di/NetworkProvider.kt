package com.yoinerdev.quizzesia.core.di

import com.yoinerdev.quizzesia.core.helpers.FirebaseAuthInterceptor
import com.yoinerdev.quizzesia.core.helpers.GeminiAuthInterceptor
import com.yoinerdev.quizzesia.data.remote.ApiGeminiService
import com.yoinerdev.quizzesia.data.remote.ApiQuizzesService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.socket.client.IO
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import io.socket.client.Socket
import java.net.URISyntaxException
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkProvider {

    @Provides
    @Singleton
    fun provideApiQuizzesService(): ApiQuizzesService {
        val client = OkHttpClient.Builder()
            .addInterceptor(FirebaseAuthInterceptor())
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://apiquizzesai.onrender.com/api/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ApiQuizzesService::class.java)
        return service
    }



    @Provides
    @Singleton
    fun provideGeminiService(): ApiGeminiService {
        val client = OkHttpClient.Builder()
            .addInterceptor(GeminiAuthInterceptor())
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://generativelanguage.googleapis.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ApiGeminiService::class.java)
    }

    @Provides
    @Singleton
    fun provideSocket(): Socket {
           return IO.socket("wss://apiquizzesai.onrender.com")
    }



}