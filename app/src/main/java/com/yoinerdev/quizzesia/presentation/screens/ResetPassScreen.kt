package com.yoinerdev.quizzesia.presentation.screens

import android.annotation.SuppressLint
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yoinerdev.quizzesia.R
import com.yoinerdev.quizzesia.core.alerts.AuthUiState
import com.yoinerdev.quizzesia.core.helpers.getLocalizedString
import com.yoinerdev.quizzesia.core.navegation.LoginScreen
import com.yoinerdev.quizzesia.core.navegation.MainScreen
import com.yoinerdev.quizzesia.core.utils.LocalAppLocale
import com.yoinerdev.quizzesia.core.utils.LocalizedStringResource
import com.yoinerdev.quizzesia.presentation.components.CustomButton
import com.yoinerdev.quizzesia.presentation.components.CustomTField
import com.yoinerdev.quizzesia.presentation.components.RotatingLoadingImage
import com.yoinerdev.quizzesia.presentation.ui.theme.BlackBackGround
import com.yoinerdev.quizzesia.presentation.viewmodels.AuthViewModel

@RequiresApi(Build.VERSION_CODES.S)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ResetPassScreen(
    viewModel: AuthViewModel,
    navController: (destination: Any) -> Unit
) {

    val context = LocalContext.current
    val uiState by viewModel.state.collectAsState()
    var isLoading by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    val logo = painterResource(R.drawable.logo)
    val locale = LocalAppLocale.current

    when (uiState) {
        is AuthUiState.IsLoading -> {
            isLoading = true
        }

        is AuthUiState.Success -> {
            isLoading = false
            navController(MainScreen)
        }

        is AuthUiState.Error -> {
            isLoading = false
            Toast.makeText(context, context.getLocalizedString(R.string.error_unknown, locale), Toast.LENGTH_LONG)
                .show()
        }

        is AuthUiState.ErrorValidEmail -> {
            isLoading = false
            Toast.makeText(
                context,
                context.getLocalizedString(R.string.error_valid_email, locale),
                Toast.LENGTH_LONG
            ).show()
        }

        is AuthUiState.ErrorUpdate -> {
            isLoading = false
            Toast.makeText(
                context,
                context.getLocalizedString(R.string.error_update, locale),
                Toast.LENGTH_LONG
            ).show()
        }

        is AuthUiState.ErrorEmpty -> {
            isLoading = false
            Toast.makeText(
                context,
                context.getLocalizedString(R.string.error_empty_fields, locale),
                Toast.LENGTH_LONG
            ).show()
        }


        else -> {}
    }



    Scaffold(
        Modifier.systemBarsPadding(),
        topBar = {
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    Icons.Filled.ArrowBackIosNew,
                    contentDescription = "iconBack",
                    tint = Color.White,
                    modifier = Modifier.clickable { navController(LoginScreen) }
                )
            }
        }
    ) { paddingValues ->

        Row(
            Modifier
                .fillMaxWidth()
                .height(20.dp)
        ) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
            )
        }

        Column(
            Modifier
                .fillMaxSize()
                .background(BlackBackGround)
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {


            //Container Top


            Box(modifier = Modifier.weight(0.3f)) {
                Column(
                    Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    RotatingLoadingImage(logo, !isLoading)
                    Text(
                        "Quiz AI Creator",
                        fontSize = 40.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(top = 10.dp),
                        color = Color.White
                    )
                    Text(
                        LocalizedStringResource(R.string.login_welcome_message),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Italic,
                        color = Color.White
                    )
                }
            }

            //container centered

            Box(
                modifier = Modifier
                    .weight(0.5f)
                    .padding(horizontal = 20.dp)
            ) {

                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(vertical = 10.dp)
                ) {
                    Text(
                        LocalizedStringResource(R.string.resetPassMessage),
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(vertical = 10.dp)
                    )
                    CustomTField(
                        value = email,
                        onValueChange = { email = it },
                        label = LocalizedStringResource(R.string.email_span_login),
                        leadingIcon = Icons.Filled.Email,
                    )

                    Spacer(
                        Modifier
                            .height(20.dp)
                            .padding(horizontal = 20.dp)
                    )

                    val resetMessage = LocalizedStringResource(R.string.Reset_successful)
                    val errorResetMessage = LocalizedStringResource(R.string.Error_resetting)

                    CustomButton(
                        onClick = {

                            viewModel.resetPassword(email) {
                                if (it) {
                                    Toast.makeText(context, resetMessage, Toast.LENGTH_LONG).show()
                                    navController(LoginScreen)
                                } else {
                                    Toast.makeText(context, errorResetMessage, Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }
                        },
                        text = { Text(LocalizedStringResource(R.string.Reset_password), fontSize = 18.sp) }
                    )

                }

            }

        }
    }

}

