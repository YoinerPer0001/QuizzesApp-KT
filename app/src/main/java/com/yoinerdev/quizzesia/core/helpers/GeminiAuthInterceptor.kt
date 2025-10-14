package com.yoinerdev.quizzesia.core.helpers

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.yoinerdev.quizzesia.GemTokens
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import okhttp3.Interceptor
import okhttp3.Response

class GeminiAuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val token = GemTokens.tokens.random()

        val request = chain.request().newBuilder().apply {
                addHeader("x-goog-api-key", token)
        }.build()

        val response = chain.proceed(request)


        return response
    }

}