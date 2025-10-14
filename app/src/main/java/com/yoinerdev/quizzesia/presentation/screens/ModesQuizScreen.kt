package com.yoinerdev.quizzesia.presentation.screens

import android.app.Activity
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yoinerdev.linternapro.presenter.components.IntersticialComponentRecompensados
import com.yoinerdev.quizzesia.IdsAdmob
import com.yoinerdev.quizzesia.R
import com.yoinerdev.quizzesia.core.alerts.SocketState
import com.yoinerdev.quizzesia.core.navegation.LobbyMultiplayerEsc
import com.yoinerdev.quizzesia.core.navegation.MainScreen
import com.yoinerdev.quizzesia.core.navegation.QuizScreenESC
import com.yoinerdev.quizzesia.core.utils.LocalizedStringResource
import com.yoinerdev.quizzesia.presentation.components.BottomBarNav
import com.yoinerdev.quizzesia.presentation.components.CustomAlertDialog
import com.yoinerdev.quizzesia.presentation.components.CustomCardModes
import com.yoinerdev.quizzesia.presentation.components.CustomOutlineButton
import com.yoinerdev.quizzesia.presentation.components.CustomSwitch
import com.yoinerdev.quizzesia.presentation.components.RotatingLoadingImage
import com.yoinerdev.quizzesia.presentation.components.TopBarMain
import com.yoinerdev.quizzesia.presentation.ui.theme.BlackBackGround
import com.yoinerdev.quizzesia.presentation.ui.theme.BlueCake
import com.yoinerdev.quizzesia.presentation.ui.theme.DarkSkyBlue
import com.yoinerdev.quizzesia.presentation.ui.theme.DarkSkyBlueCake
import com.yoinerdev.quizzesia.presentation.ui.theme.OrangeTransparent
import com.yoinerdev.quizzesia.presentation.ui.theme.SecondaryGray
import com.yoinerdev.quizzesia.presentation.viewmodels.AuthViewModel
import com.yoinerdev.quizzesia.presentation.viewmodels.QuizzesSocketVM

@Composable
fun ModesQuizScreen(
    id: String,
    authVm: AuthViewModel,
    socketVM: QuizzesSocketVM,
    navController: (Any) -> Unit
) {


    val state by socketVM.state.collectAsState()
    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current

    when (state) {
        SocketState.IsLoading -> {
            isLoading = true
        }

        SocketState.SuccessCreatedRoom -> {
            navController(LobbyMultiplayerEsc(id))
        }

        SocketState.SuccessCreatedSinglePlayer -> {
            navController(QuizScreenESC(typeQuiz = 0))
        }

        SocketState.SuccessStartGame -> {
            navController(QuizScreenESC(typeQuiz = 1))
        }

        SocketState.ErrorToConnect -> {
            Toast.makeText(
                context,
                LocalizedStringResource(R.string.error_to_connect),
                Toast.LENGTH_SHORT
            ).show()
            isLoading = false
        }

        SocketState.ErrorToCreateRoom -> {
            Toast.makeText(
                context,
                LocalizedStringResource(R.string.error_to_create_room),
                Toast.LENGTH_SHORT
            ).show()
            isLoading = false
        }

        else -> {
            isLoading = false
        }
    }

    LaunchedEffect(Unit) {
        socketVM.onGameStarted()
        socketVM.onCharge() //conectamos al socket
    }


    val attemptsRemaining by authVm.attempsRemaining.collectAsState()
    val imageSinglePlayer = painterResource(R.drawable.singlep)
    val imageMultiPlayer = painterResource(R.drawable.multip)
    var showDialogAddAttempt by remember { mutableStateOf(false) }
    var ShowIntersticial by remember { mutableStateOf(false) }
    val activity = context as Activity
    var showDialogNoAdds by remember { mutableStateOf(false) }
    var showDialogQuota by remember { mutableStateOf(false) }
    var showDialogPdfFeature by remember { mutableStateOf(false) }


    if(ShowIntersticial){
        IntersticialComponentRecompensados(IdsAdmob.ID_INSTERSTICIAL_ADD_CREDITS) { ad ->
            if (ad != null) {
                ad.show(activity) {
                    ShowIntersticial = false
                    authVm.updateUserAttempts()
                }
            }else{
                if(ShowIntersticial){
                    showDialogNoAdds = true
                    ShowIntersticial = false
                }
            }
        }
    }

    if (isLoading) {
        Box(
            Modifier
                .fillMaxSize()
                .background(BlackBackGround)
        ) {
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                RotatingLoadingImage(stop = false)
                Text(
                    LocalizedStringResource(R.string.loading),
                    fontSize = 18.sp,
                    fontStyle = FontStyle.Italic,
                    color = Color.White,
                    modifier = Modifier.padding(vertical = 20.dp)
                )
            }
        }
    } else {


        Scaffold(
            Modifier.systemBarsPadding(),
            topBar = {
                TopBarMain(
                    icon = Icons.Outlined.CheckCircle,
                    text = LocalizedStringResource(R.string.generated_success),
                    attemptsRemaining = attemptsRemaining,
                    fontSize = 20,
                    subtitle = LocalizedStringResource(R.string.choose_how_to_use)
                ) {
                    showDialogAddAttempt = true
                }
            }

        ) { paddingValues ->

            AnimatedVisibility(showDialogPdfFeature) {
                CustomAlertDialog(
                    showDialog = showDialogPdfFeature,
                    title = LocalizedStringResource(R.string.pdf_feature_title),
                    messages = listOf(
                        LocalizedStringResource(R.string.pdf_feature_msg_1),
                        LocalizedStringResource(R.string.pdf_feature_msg_2)
                    ),
                    onDismiss = { showDialogPdfFeature = false }
                )

            }


            AnimatedVisibility(showDialogAddAttempt) {
                CustomAlertDialog(
                    showDialog = showDialogQuota,
                    title = LocalizedStringResource(R.string.get_more_credits_title),
                    messages = listOf(
                        LocalizedStringResource(R.string.watch_video_earn_credits),
                        LocalizedStringResource(R.string.wait_credits_renew)
                    ),
                    onDismiss = { showDialogQuota = false },
                    confirmAction = {
                        ShowIntersticial = true
                        showDialogQuota = false
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

            Column(
                Modifier
                    .fillMaxSize()
                    .background(BlackBackGround)
                    .padding(paddingValues)
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    Modifier
                        .weight(0.5f)
                        .fillMaxWidth()
                        .padding(top = 50.dp)
                ) {
                    Column(
                        Modifier
                            .fillMaxSize()
                            .clip(ShapeDefaults.Medium)
                            .background(SecondaryGray)
                            .padding(vertical = 20.dp, horizontal = 20.dp),

                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {


                        Text(
                            LocalizedStringResource(R.string.practice_modes),
                            fontSize = 20.sp,
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(bottom = 10.dp)
                        )

                        Column(
                            Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.SpaceAround,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            CustomCardModes(
                                imageSinglePlayer,
                                LocalizedStringResource(R.string.singleplayer_title),
                                LocalizedStringResource(R.string.singleplayer_desc)
                            ) {
                                socketVM.startSingleGame(id)
                            }

                            CustomCardModes(
                                imageMultiPlayer,
                                LocalizedStringResource(R.string.multiplayer_title),
                                LocalizedStringResource(R.string.multiplayer_desc)
                            ) {
                                socketVM.createRoom(quizId = id)
                            }


                        }


                    }
                }

                Box(
                    Modifier
                        .weight(0.4f)
                        .fillMaxWidth()
                ) {
                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(top = 30.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {

                            ElevatedCard(
                                onClick = {showDialogPdfFeature = true},
                                colors = CardDefaults.cardColors(
                                    containerColor = OrangeTransparent
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                            ) {
                                Row(
                                    Modifier
                                        .fillMaxSize()
                                        .padding(10.dp),
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        Modifier
                                            .size(80.dp)
                                            .clip(CircleShape)
                                            .background(Color.Transparent)
                                            .border(2.dp, Color.White, shape = CircleShape),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            Icons.Default.Download,
                                            contentDescription = "iconDownload",
                                            modifier = Modifier
                                                .size(50.dp)
                                                .padding(5.dp),
                                            tint = Color.White
                                        )
                                    }
                                    Box(Modifier.fillMaxWidth()) {
                                        Column(
                                            Modifier
                                                .fillMaxSize()
                                                .padding(start = 10.dp),
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Text(
                                                LocalizedStringResource(R.string.download_pdf),
                                                fontSize = 18.sp,
                                                color = Color.White,
                                                fontWeight = FontWeight.SemiBold,
                                                modifier = Modifier.padding(start = 10.dp)
                                            )
                                            HorizontalDivider(
                                                thickness = 1.dp,
                                                color = Color.LightGray,
                                                modifier = Modifier
                                                    .padding(vertical = 5.dp)
                                                    .padding(start = 10.dp)
                                            )
                                            Row(
                                                Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.Center,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                CustomSwitch(
                                                    LocalizedStringResource(R.string.include_answer_sheet),
                                                    fontSize = 15.sp
                                                ) {

                                                }
                                            }
                                        }
                                    }
                                }
                            }

                        }

                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(top = 30.dp)
                        ) {
                            CustomOutlineButton(
                                onClick = {
                                    navController(MainScreen)
                                    socketVM.disconnect()
                                },
                                contentColor = BlueCake,
                                width = 0.2f
                            ) {
                                Icon(
                                    Icons.Default.ArrowBackIosNew,
                                    "abcnIcon",
                                    modifier = Modifier.size(50.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}