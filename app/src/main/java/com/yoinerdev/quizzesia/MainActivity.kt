package com.yoinerdev.quizzesia


import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowInsetsController
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.ads.MobileAds
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.yoinerdev.quizzesia.core.helpers.getSavedLanguage
import com.yoinerdev.quizzesia.core.helpers.saveLanguage
import com.yoinerdev.quizzesia.core.navegation.NavigationManager
import com.yoinerdev.quizzesia.core.utils.LocalAppLocale
import com.yoinerdev.quizzesia.presentation.ui.theme.MainOrange
import com.yoinerdev.quizzesia.presentation.ui.theme.QuizzesIATheme
import com.yoinerdev.quizzesia.presentation.viewmodels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels()

    @Suppress("DEPRECATION")
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen()
        super.onCreate(savedInstanceState)

        CoroutineScope(Dispatchers.IO).launch {
            // Initialize the Google Mobile Ads SDK on a background thread.
            MobileAds.initialize(this@MainActivity) {}
        }

        // Tu color oscuro
        val darkColor = Color(0xFF262D40).toArgb()

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                lightScrim = darkColor,
                darkScrim = darkColor
            ),
            navigationBarStyle = SystemBarStyle.auto(
                lightScrim = darkColor,
                darkScrim = darkColor
            )
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Android 11 (API 30)
            window.insetsController?.setSystemBarsAppearance(
                0,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
            window.statusBarColor = darkColor
            window.navigationBarColor = darkColor
        }

        val context = this



        setContent {

            val langCode by getSavedLanguage(context).collectAsState(
                initial = Locale.getDefault().language
            )
            var appLocale by remember(langCode) { mutableStateOf(Locale(langCode)) }
            val scope = rememberCoroutineScope()

            CompositionLocalProvider(LocalAppLocale provides appLocale) {
                QuizzesIATheme {
                    NavigationManager(
                        authViewModel = authViewModel,
                        onLanguageChange = { langCode ->
                            appLocale = Locale(langCode)

                            scope.launch {
                                saveLanguage(context, langCode) // ✅ persiste idioma
                            }
                        }
                    )
                }
            }
        }
    }

    fun launchGoogleSignIn(error:()->Unit) {
        lifecycleScope.launch {
            try {
                val googleIdOption = GetGoogleIdOption.Builder()
                    .setServerClientId(getString(R.string.default_web_client_id))
                    .setFilterByAuthorizedAccounts(false)
                    .build()

                val request = GetCredentialRequest.Builder()
                    .addCredentialOption(googleIdOption)
                    .build()

                val credentialManager = CredentialManager.create(this@MainActivity)

                val result = credentialManager.getCredential(this@MainActivity, request)

                // 👉 aquí pasas el resultado al ViewModel
                authViewModel.handleSignIn(result.credential)

            } catch (e: Exception) {
                error()
                Log.e("Auth", "Error en sign in", e)
            }
        }
    }


}



