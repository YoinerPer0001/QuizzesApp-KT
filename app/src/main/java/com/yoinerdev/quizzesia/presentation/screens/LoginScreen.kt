package com.yoinerdev.quizzesia.presentation.screens

import android.annotation.SuppressLint
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yoinerdev.quizzesia.MainActivity
import com.yoinerdev.quizzesia.R
import com.yoinerdev.quizzesia.core.alerts.AuthUiState
import com.yoinerdev.quizzesia.core.helpers.getLocalizedString
import com.yoinerdev.quizzesia.core.navegation.MainScreen
import com.yoinerdev.quizzesia.core.navegation.RegisterEsc
import com.yoinerdev.quizzesia.core.navegation.ResetPassEsc
import com.yoinerdev.quizzesia.core.utils.LocalAppLocale
import com.yoinerdev.quizzesia.core.utils.LocalizedStringResource
import com.yoinerdev.quizzesia.presentation.components.CustomButton
import com.yoinerdev.quizzesia.presentation.components.CustomOutlineButton
import com.yoinerdev.quizzesia.presentation.components.CustomTField
import com.yoinerdev.quizzesia.presentation.components.CustomTField2
import com.yoinerdev.quizzesia.presentation.components.RotatingLoadingImage
import com.yoinerdev.quizzesia.presentation.ui.theme.BlackBackGround
import com.yoinerdev.quizzesia.presentation.ui.theme.BlueCake
import com.yoinerdev.quizzesia.presentation.ui.theme.MainOrange
import com.yoinerdev.quizzesia.presentation.viewmodels.AuthViewModel

@RequiresApi(Build.VERSION_CODES.S)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(
    viewModel: AuthViewModel,
    navController: (destination: Any) -> Unit
) {

    val context = LocalContext.current
    val uiState by viewModel.state.collectAsState()
    var isLoading by remember { mutableStateOf(false) }
    var isLoadingGoogle by remember { mutableStateOf(false) }
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    val logo = painterResource(R.drawable.logo)
    val googleLogo = painterResource(R.drawable.google)
    val locale = LocalAppLocale.current
    val loggedUser by viewModel.logged.collectAsState()

    LaunchedEffect (Unit) {
        viewModel.getUserSesion()
    }


    when (uiState) {
        is AuthUiState.IsLoading -> {
            isLoading = true
            isLoadingGoogle = false
        }

        is AuthUiState.Success -> {
            isLoading = false
            isLoadingGoogle = false
            navController(MainScreen)
        }

        is AuthUiState.Error -> {
            isLoading = false
            isLoadingGoogle = false
            Toast.makeText(context, context.getLocalizedString(R.string.error_unknown, locale), Toast.LENGTH_LONG).show()
        }
        is AuthUiState.ErrorValidEmail -> {
            isLoading = false
            isLoadingGoogle = false
            Toast.makeText(context, context.getLocalizedString(R.string.error_valid_email, locale), Toast.LENGTH_LONG).show()
        }
        is AuthUiState.ErrorEmpty -> {
            isLoading = false
            isLoadingGoogle = false
            Toast.makeText(context, context.getLocalizedString(R.string.error_empty_fields, locale), Toast.LENGTH_LONG).show()
        }
        is AuthUiState.ErrorLengthData -> {
            isLoading = false
            isLoadingGoogle = false
            Toast.makeText(context, context.getLocalizedString(R.string.error_password_length, locale), Toast.LENGTH_LONG).show()
        }
        is AuthUiState.ErrorRegister -> {
            isLoading = false
            isLoadingGoogle = false
            Toast.makeText(context, context.getLocalizedString(R.string.error_register, locale), Toast.LENGTH_LONG).show()
        }
        is AuthUiState.ErrorAuth -> {
            isLoading = false
            isLoadingGoogle = false
            Toast.makeText(context, context.getLocalizedString(R.string.error_auth, locale), Toast.LENGTH_LONG).show()
        }
        is AuthUiState.ErrorAuthGoogle -> {
            isLoading = false
            isLoadingGoogle = false
            Toast.makeText(context, context.getLocalizedString(R.string.error_auth_google, locale), Toast.LENGTH_LONG).show()
        }
        is AuthUiState.ErrorAuthCredential -> {
            isLoading = false
            isLoadingGoogle = false
            Toast.makeText(context, context.getLocalizedString(R.string.error_auth_credential, locale), Toast.LENGTH_LONG).show()
        }

        else -> {}
    }



    Scaffold(Modifier.systemBarsPadding()) { paddingValues ->

        Row(Modifier
            .fillMaxWidth()
            .height(20.dp)) {
            LinearProgressIndicator(modifier = Modifier
                .fillMaxWidth()
                .height(20.dp))
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

            Box(modifier = Modifier
                .weight(0.35f)
                .padding(horizontal = 20.dp)) {

                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(vertical = 10.dp)
                ) {
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

                    Spacer(Modifier
                        .height(20.dp)
                        .padding(horizontal = 20.dp))

                    CustomButton(
                        onClick = {

                            viewModel.loginWithEmail(email, password)
                        },
                        text = { Text(LocalizedStringResource(R.string.btn_login), fontSize = 18.sp) }
                    )

                    Text(
                        LocalizedStringResource(R.string.forgot_password_text),
                        color = Color.LightGray,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp).clickable { navController(ResetPassEsc) },
                        textAlign = TextAlign.Center
                    )

                    Row {
                        Box(
                            Modifier
                                .weight(0.5f)
                                .fillMaxHeight(),
                            contentAlignment = Alignment.Center
                        ) {
                            Spacer(
                                Modifier
                                    .fillMaxWidth()
                                    .height(1.dp)
                                    .background(Color.LightGray)
                            )
                        }
                        Box(
                            Modifier
                                .weight(0.1f)
                                .fillMaxHeight(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(LocalizedStringResource(R.string.or), fontSize = 18.sp, color = Color.LightGray)
                        }
                        Box(
                            Modifier
                                .weight(0.5f)
                                .fillMaxHeight(),
                            contentAlignment = Alignment.Center
                        ) {
                            Spacer(
                                Modifier
                                    .fillMaxWidth()
                                    .height(1.dp)
                                    .background(Color.LightGray)
                            )
                        }
                    }

                }

            }

            //Bottom container

            Box(
                modifier = Modifier
                    .weight(0.25f)
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CustomButton(
                        containerColor = Color.White,
                        contentColor = MainOrange,
                        colorShadow = Color.Transparent,
                        onClick = {
                            isLoadingGoogle = true
                            (context as MainActivity).launchGoogleSignIn{
                                isLoadingGoogle = false
                            }
                        },
                        text = {
                            if(!isLoadingGoogle){
                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Image(
                                        googleLogo,
                                        contentDescription = "google",
                                        contentScale = ContentScale.Fit,
                                        modifier = Modifier.size(25.dp)
                                    )
                                    Text(
                                        LocalizedStringResource(R.string.btn_google_login),
                                        fontSize = 18.sp,
                                        modifier = Modifier.padding(horizontal = 10.dp)
                                    )
                                }
                            }else{
                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    CircularProgressIndicator(color = MainOrange)
                                }
                            }

                        }
                    )

                    Text(
                        LocalizedStringResource(R.string.No_have_account),
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        modifier = Modifier.padding(top = 30.dp, bottom = 10.dp)
                    )


                    CustomOutlineButton(
                        onClick = {navController(RegisterEsc)},
                        contentColor = BlueCake,
                        text = { Text(LocalizedStringResource(R.string.Sign_up), fontSize = 18.sp, color = BlueCake) }
                    )

                }

            }


        }
    }

}

