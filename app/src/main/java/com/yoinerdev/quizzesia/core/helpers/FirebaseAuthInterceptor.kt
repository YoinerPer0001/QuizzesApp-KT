package com.yoinerdev.quizzesia.core.helpers

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import okhttp3.Interceptor
import okhttp3.Response

class FirebaseAuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val token = runBlocking {
            FirebaseAuth.getInstance().currentUser?.getIdToken(false)?.await()?.token
        }

        Log.d("EL TOKEN INTERCEPTOR", token.toString())

        val request = chain.request().newBuilder().apply {
            if(!token.isNullOrEmpty()){
                addHeader("Authorization", "Bearer $token")
            }
        }.build()

        val response = chain.proceed(request)

        // Si el backend rechaza (token expirado o inválido)
        if (response.code == 401 && FirebaseAuth.getInstance().currentUser != null) {
            response.close() // cerrar para no filtrar conexión

            val newToken = runBlocking {
                FirebaseAuth.getInstance().currentUser?.getIdToken(true)?.await()?.token
            }

            val newRequest = chain.request().newBuilder().apply {
                if (!newToken.isNullOrEmpty()) {
                    addHeader("Authorization", "Bearer $newToken")
                }
            }.build()

            return chain.proceed(newRequest)
        }

        return response
    }

}