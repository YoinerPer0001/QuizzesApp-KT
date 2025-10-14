package com.yoinerdev.quizzesia.presentation.screens

import android.app.Activity
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import coil.decode.ImageSource
import com.yoinerdev.linternapro.presenter.components.IntersticialComponentRecompensados
import com.yoinerdev.quizzesia.IdsAdmob
import com.yoinerdev.quizzesia.R
import com.yoinerdev.quizzesia.core.helpers.getSavedLanguage
import com.yoinerdev.quizzesia.core.helpers.saveLanguage
import com.yoinerdev.quizzesia.core.navegation.LoginScreen
import com.yoinerdev.quizzesia.presentation.components.CardEstatistics
import com.yoinerdev.quizzesia.presentation.components.CustomButton
import com.yoinerdev.quizzesia.presentation.components.SettingItem
import com.yoinerdev.quizzesia.presentation.components.TopBarMain
import com.yoinerdev.quizzesia.presentation.ui.theme.BlackBackGround
import com.yoinerdev.quizzesia.presentation.ui.theme.BlueCake
import com.yoinerdev.quizzesia.presentation.ui.theme.MainOrange
import com.yoinerdev.quizzesia.presentation.ui.theme.RedColorCake
import com.yoinerdev.quizzesia.presentation.ui.theme.SemiTransparent
import com.yoinerdev.quizzesia.presentation.viewmodels.AuthViewModel
import com.yoinerdev.quizzesia.core.utils.LanguageOptions
import com.yoinerdev.quizzesia.core.utils.LocalAppLocale
import com.yoinerdev.quizzesia.core.utils.LocalizedStringResource
import com.yoinerdev.quizzesia.presentation.components.BottomBarNav
import com.yoinerdev.quizzesia.presentation.components.CustomAlertDialog
import com.yoinerdev.quizzesia.presentation.components.animations.LottieAnimationScore
import com.yoinerdev.quizzesia.presentation.ui.theme.SecondaryGray
import kotlinx.coroutines.flow.first

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(
    viewModel: AuthViewModel,
    navController: (Any) -> Unit,
    changeLanguage: (String) -> Unit
) {
    val attemptsRemaining by viewModel.attempsRemaining.collectAsState()
    val scrollState = rememberScrollState()

    var expandedLanguage by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(LanguageOptions[0]) }

    val user by viewModel.user.collectAsState()

    val logo = painterResource(R.drawable.logo)
    val context = LocalContext.current

    var showDialogSuccess by remember { mutableStateOf(false) }
    var showDialogError by remember { mutableStateOf(false) }

    var showDialogAddAttempt by remember { mutableStateOf(false) }
    var ShowIntersticial by remember { mutableStateOf(false) }
    val activity = context as Activity
    var showDialogNoAdds by remember { mutableStateOf(false) }

    if(ShowIntersticial){
        IntersticialComponentRecompensados(IdsAdmob.ID_INSTERSTICIAL_ADD_CREDITS) { ad ->
            if (ad != null) {
                ad.show(activity) {
                    ShowIntersticial = false
                    viewModel.updateUserAttempts()
                }
            }else{
                if(ShowIntersticial){
                    showDialogNoAdds = true
                    ShowIntersticial = false
                }
            }
        }
    }

    Scaffold(
        Modifier.systemBarsPadding(),
        topBar = {
            TopBarMain(
                logo,
                attemptsRemaining,
                text = LocalizedStringResource(R.string.my_profile),
                clipSize = 0f
            ) {
                showDialogAddAttempt = true
            }

        },
        bottomBar = {
            BottomBarNav(user, btnSelected = 3) {
                navController(it)
            }
        }
    ) { paddingValues ->

        Column(
            Modifier
                .fillMaxSize()
                .background(BlackBackGround)
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            AnimatedVisibility(showDialogAddAttempt) {
                CustomAlertDialog(
                    showDialog = showDialogAddAttempt,
                    title = LocalizedStringResource(R.string.get_more_credits_title),
                    messages = listOf(
                        LocalizedStringResource(R.string.watch_video_earn_credits),
                        LocalizedStringResource(R.string.wait_credits_renew)
                    ),
                    onDismiss = { showDialogAddAttempt = false },
                    confirmAction = {
                        ShowIntersticial = true
                        showDialogAddAttempt = false
                    },
                    confirmText = LocalizedStringResource(R.string.understood)
                )


            }

            AnimatedVisibility(showDialogNoAdds) {
                CustomAlertDialog(
                    showDialog = showDialogNoAdds,
                    title = LocalizedStringResource(R.string.no_ads_title),
                    messages = listOf(
                        LocalizedStringResource(R.string.no_ads_msg_1),
                        LocalizedStringResource(R.string.no_ads_msg_2)
                    ),
                    onDismiss = { showDialogNoAdds = false },
                    confirmAction = { showDialogNoAdds = false },
                    confirmText = LocalizedStringResource(R.string.understood),
                    dismissAction = { showDialogAddAttempt = true },
                    dismissText = LocalizedStringResource(R.string.retry)
                )
            }

            Box(
                Modifier
                    .weight(0.25f)
                    .padding()
                    .clip(shape = RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp))
                    .background(
                        SecondaryGray
                    )
            ) {
                Column(
                    Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    HorizontalDivider(
                        modifier = Modifier.padding(top = 5.dp),
                        color = Color.White,
                        thickness = 1.dp
                    )
                    Box(contentAlignment = Alignment.CenterEnd) {
                        Column (Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                            if (user?.isGoogleProvider == true) {
                                AsyncImage(
                                    user?.photoUrl,
                                    contentDescription = "imagePerfil",
                                    contentScale = ContentScale.Fit,
                                    modifier = Modifier
                                        .size(150.dp)
                                        .clip(CircleShape)
                                        .border(2.dp, shape = CircleShape, color = Color.White)
                                )
                            } else {
                                Box(
                                    Modifier
                                        .size(150.dp)
                                        .clip(shape = CircleShape)
                                        .background(MainOrange), contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        user?.displayName?.firstOrNull()?.uppercase() ?: "",
                                        color = Color.White,
                                        fontSize = 80.sp
                                    )
                                }
                            }

                            Row {
                                Text(
                                    user?.displayName.toString(),
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.White,
                                    modifier = Modifier.padding(top = 20.dp)
                                )

                            }
                            Text(
                                user?.email.toString(),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Light,
                                fontStyle = FontStyle.Italic,
                                color = Color.White,
                                modifier = Modifier.padding(bottom = 20.dp)
                            )
                        }

                    }


                }
            }
//            Box(Modifier
//                .weight(0.05f)
//                .padding(horizontal = 30.dp, vertical = 10.dp)) {
//                CardEstatistics(
//                    "Estadísticas rápidas",
//                    "100%",
//                    sub1 = "Completadas",
//                    value2 = "1h",
//                    sub2 = "Total Jugado",
//                    value3 = "30%",
//                    sub3 = "Prom. Aciertos"
//                )
//
//            }



            Box(
                Modifier
                    .weight(0.3f)
                    .padding(horizontal = 30.dp, vertical = 20.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = LocalizedStringResource(R.string.account_settings),
                        fontSize = 18.sp,
                        modifier = Modifier.padding(bottom = 20.dp)
                    )
                    Box(Modifier.fillMaxWidth()) {
                        Column {
                            SettingItem(
                                onclick = { expandedLanguage = true },
                                text = LanguageOptions.firstOrNull { it.first == LocalAppLocale.current.language }?.second
                                    ?: "Unknown",
                                icon = Icons.Default.Translate
                            )

                            DropdownMenu(
                                modifier = Modifier.fillMaxWidth(0.85f),
                                expanded = expandedLanguage,
                                onDismissRequest = { expandedLanguage = false }
                            ) {
                                LanguageOptions.forEach { option ->
                                    DropdownMenuItem(
                                        text = { Text(option.second) },
                                        onClick = {
                                            selectedOption = option
                                            saveLanguage(
                                                context,
                                                selectedOption.first
                                            ) // guarda en datastore
                                            changeLanguage(selectedOption.first) //
                                            expandedLanguage = false
                                        }
                                    )
                                }
                            }

                            HorizontalDivider(
                                modifier = Modifier.padding(top = 5.dp),
                                color = Color.White,
                                thickness = 1.dp
                            )
                        }

                    }

                    AnimatedVisibility(showDialogSuccess) {
                        AlertDialog(
                            onDismissRequest = { showDialogSuccess = true },
                            title = { Text(text = LocalizedStringResource(R.string.email_sent_title)) },
                            text = { Text(LocalizedStringResource(R.string.email_sent_msg)) },
                            confirmButton = {
                                TextButton(onClick = { showDialogSuccess = false }) {
                                    Text(LocalizedStringResource(R.string.accept))
                                }
                            }
                        )

                    }

                    AnimatedVisibility(user?.isGoogleProvider == false) {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp)
                        ) {
                            Column {
                                SettingItem(
                                    onclick = {
                                        viewModel.resetPassword(user?.email.toString()) {
                                            if (it) {
                                                showDialogSuccess = true
                                            } else {
                                                showDialogError = true
                                            }
                                        }
                                    },
                                    text = LocalizedStringResource(R.string.Reset_password),
                                    icon = Icons.Default.Lock
                                )
                                HorizontalDivider(
                                    modifier = Modifier.padding(top = 5.dp),
                                    color = Color.White,
                                    thickness = 1.dp
                                )
                            }

                        }
                    }

                    Box(
                        Modifier
                            .padding(top = 40.dp)
                            .fillMaxWidth()
                    ) {
                        CustomButton(
                            onClick = {
                                viewModel.signOut()
                                navController(LoginScreen)
                            },
                            width = 0.4f,
                            containerColor = RedColorCake,
                            colorShadow = Color.Transparent
                        ) {
                            Text(
                                text = LocalizedStringResource(R.string.logout),
                                color = Color.White,
                                fontSize = 15.sp
                            )

                        }
                    }

                }
            }


        }
    }
}