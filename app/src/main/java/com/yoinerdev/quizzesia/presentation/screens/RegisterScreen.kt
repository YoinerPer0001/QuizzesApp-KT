package com.yoinerdev.quizzesia.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yoinerdev.quizzesia.R
import com.yoinerdev.quizzesia.core.alerts.AuthUiState
import com.yoinerdev.quizzesia.core.helpers.getLocalizedString
import com.yoinerdev.quizzesia.core.navegation.LoginScreen
import com.yoinerdev.quizzesia.core.utils.LocalAppLocale
import com.yoinerdev.quizzesia.core.utils.LocalizedStringResource
import com.yoinerdev.quizzesia.presentation.components.CustomButton
import com.yoinerdev.quizzesia.presentation.components.CustomOutlineButton
import com.yoinerdev.quizzesia.presentation.components.CustomTField
import com.yoinerdev.quizzesia.presentation.components.CustomTField2
import com.yoinerdev.quizzesia.presentation.components.RotatingLoadingImage
import com.yoinerdev.quizzesia.presentation.ui.theme.BlackBackGround
import com.yoinerdev.quizzesia.presentation.viewmodels.AuthViewModel

@Composable
fun RegisterScreen(
    viewModel: AuthViewModel,
    navController: (Any) -> Unit
) {

    val uiState by viewModel.state.collectAsState()
    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val user by viewModel.user.collectAsState()
    var name by remember { mutableStateOf("") }
    val email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val locale = LocalAppLocale.current

    when (uiState) {
        is AuthUiState.IsLoading -> {
            isLoading = true
        }

        is AuthUiState.Success -> {
            isLoading = false

            navController(com.yoinerdev.quizzesia.core.navegation.MainScreen)
        }

        is AuthUiState.Error -> {
            isLoading = false
            Toast.makeText(context, context.getLocalizedString(R.string.error_unknown, locale), Toast.LENGTH_LONG).show()
        }
        is AuthUiState.ErrorValidEmail -> {
            isLoading = false
            Toast.makeText(context, context.getLocalizedString(R.string.error_valid_email, locale), Toast.LENGTH_LONG).show()
        }
        is AuthUiState.ErrorEmpty -> {
            isLoading = false
            Toast.makeText(context, context.getLocalizedString(R.string.error_empty_fields, locale), Toast.LENGTH_LONG).show()
        }
        is AuthUiState.ErrorInvalidName -> {
            isLoading = false
            Toast.makeText(context, context.getLocalizedString(R.string.error_invalid_name, locale), Toast.LENGTH_LONG).show()
        }
        is AuthUiState.ErrorLengthData -> {
            isLoading = false
            Toast.makeText(context, context.getLocalizedString(R.string.error_password_length, locale), Toast.LENGTH_LONG).show()
        }
        is AuthUiState.ErrorRegister -> {
            isLoading = false
            Toast.makeText(context, context.getLocalizedString(R.string.error_register, locale), Toast.LENGTH_LONG).show()
        }

        is AuthUiState.ErrorUpdate -> {
            isLoading = false
            Toast.makeText(
                context,
                context.getLocalizedString(R.string.error_update, locale),
                Toast.LENGTH_LONG
            ).show()
        }

        else -> {}
    }

    Scaffold(Modifier.systemBarsPadding()) { paddingValues ->

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

            var email by remember { mutableStateOf("") }

            val logo = painterResource(R.drawable.logo)
            val googleLogo = painterResource(R.drawable.google)

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
                    .weight(0.35f)
                    .padding(horizontal = 20.dp)
            ) {

                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(vertical = 10.dp)
                ) {
                    CustomTField(
                        value = name,
                        onValueChange = { name = it },
                        label = LocalizedStringResource(R.string.name_spam),
                        leadingIcon = Icons.Filled.Person,
                    )

                    CustomTField(
                        value = email,
                        onValueChange = { email = it },
                        label = LocalizedStringResource(R.string.email_span_login),
                        leadingIcon = Icons.Filled.Email,
                    )

                    CustomTField2(
                        value = password,
                        onValueChange = { password = it },
                        label = LocalizedStringResource(R.string.password_span_login),
                        leadingIcon = Icons.Filled.Lock,
                        visualTransformation = PasswordVisualTransformation(),
                        trailingIcon = Icons.Filled.Visibility,
                    ){}

                    Spacer(
                        Modifier
                            .height(20.dp)
                            .padding(horizontal = 20.dp)
                    )

                    CustomButton(
                        onClick = { viewModel.register(name, email, password) },
                        text = { Text(LocalizedStringResource(R.string.Sign_up), fontSize = 18.sp, color = Color.White) }
                    )

                }

            }

            Box(
                modifier = Modifier
                    .weight(0.25f)
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Text(
                        LocalizedStringResource(R.string.have_an_account),
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 30.dp, bottom = 10.dp),
                        color = Color.White
                    )


                    CustomOutlineButton(
                        onClick = { navController(LoginScreen) },
                        text = { Text(LocalizedStringResource(R.string.btn_login), fontSize = 18.sp, color = Color.White) }
                    )

                }

            }

        }
    }
}