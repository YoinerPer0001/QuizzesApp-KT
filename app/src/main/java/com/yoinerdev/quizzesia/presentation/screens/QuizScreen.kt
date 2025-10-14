package com.yoinerdev.quizzesia.presentation.screens

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LockClock
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yoinerdev.linternapro.presenter.components.BannerComponent
import com.yoinerdev.linternapro.presenter.components.IntersticialComponent
import com.yoinerdev.quizzesia.IdsAdmob
import com.yoinerdev.quizzesia.R
import com.yoinerdev.quizzesia.core.alerts.SocketState
import com.yoinerdev.quizzesia.core.navegation.MainScreen
import com.yoinerdev.quizzesia.core.navegation.PodiumScreenESC
import com.yoinerdev.quizzesia.core.utils.LocalizedStringResource
import com.yoinerdev.quizzesia.presentation.components.BottomBarNav
import com.yoinerdev.quizzesia.presentation.components.CountdownTimer
import com.yoinerdev.quizzesia.presentation.components.CustomAnswerOption
import com.yoinerdev.quizzesia.presentation.components.CustomButton
import com.yoinerdev.quizzesia.presentation.components.RotatingLoadingImage
import com.yoinerdev.quizzesia.presentation.components.TopBarMain
import com.yoinerdev.quizzesia.presentation.components.TopBarNoTorch
import com.yoinerdev.quizzesia.presentation.components.animations.LottieAnimationScore
import com.yoinerdev.quizzesia.presentation.ui.theme.BlackBackGround
import com.yoinerdev.quizzesia.presentation.ui.theme.MainOrange
import com.yoinerdev.quizzesia.presentation.viewmodels.QuizzesSocketVM
import kotlinx.coroutines.delay

@SuppressLint("DefaultLocale")
@Composable
fun QuizScreen(
    quizType:Int,
    socketVM: QuizzesSocketVM,
    navController: (Any) -> Unit,
) {



    var selectedAnswer by remember { mutableStateOf<String?>(null) }
    var actualPercent by remember { mutableFloatStateOf(0f) }
    val actualQuestion by socketVM.actualQuestions.collectAsState()
    val actualScore by socketVM.userScore.collectAsState()
    val roomData by socketVM.roomData.collectAsState()
    val state by socketVM.state.collectAsState()
    val playerLeave by socketVM.playerLeave.collectAsState()
    val context = LocalContext.current
    var showAlertDialog by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isLoading = true
        socketVM.questionListener() //eventos generados al pedir la question
        socketVM.getQuestion()
    }


    val tips = listOf(
        LocalizedStringResource(R.string.tip_1),
        LocalizedStringResource(R.string.tip_2),
        LocalizedStringResource(R.string.tip_3),
        LocalizedStringResource(R.string.tip_4),
        LocalizedStringResource(R.string.tip_5)
    )


    val randomTip = remember(actualQuestion) { tips.random() }

    var ShowIntersticial by remember { mutableStateOf(false) }
    val activity = context as Activity
    var percentTimeSelectedAnswer by remember { mutableStateOf("") }

    LaunchedEffect(selectedAnswer) {
        percentTimeSelectedAnswer = (1f - actualPercent).toString()
        if(quizType == 0 && !selectedAnswer.isNullOrEmpty()){
            socketVM.checkQuestion(selectedAnswer, percentTimeSelectedAnswer)
        }
    }

    var showSuccessCheck by remember { mutableStateOf(false) }
    var showErrorCheck by remember { mutableStateOf(false) }
    var showConfetti by remember { mutableStateOf(false) }

    BackHandler {
        showAlertDialog = true
    }


    when (state) {
        SocketState.IsLoading -> {
            isLoading = true
        }

        SocketState.SuccessCheckQuestion -> {
            if(actualQuestion?.questionIndex === roomData?.numberQuestions){
                showConfetti = true
            }
            socketVM.getQuestion()
            showSuccessCheck = false
            showErrorCheck = false
            isLoading = false
        }

        SocketState.SuccessAnswer ->{
            showSuccessCheck = true
        }

        SocketState.ErrorAnswer ->{
            showErrorCheck = true
        }

        SocketState.ErrorToCheckQuestion -> {
            isLoading = false
            Toast.makeText(
                context,
                LocalizedStringResource(R.string.server_error),
                Toast.LENGTH_SHORT
            ).show()
        }

        SocketState.HostLeave -> {
            isLoading = false
            navController(MainScreen)
            Toast.makeText(
                context,
                "$playerLeave ${LocalizedStringResource(R.string.host_left_player)}",
                Toast.LENGTH_SHORT
            ).show()
        }


        SocketState.PlayerLeave -> {
            isLoading = false
            Toast.makeText(
                context,
                "$playerLeave ${LocalizedStringResource(R.string.player_left)}",
                Toast.LENGTH_SHORT
            ).show()
        }

        SocketState.InsParticipants -> {
            isLoading = false
            Toast.makeText(
                context,
                LocalizedStringResource(R.string.insufficient_participants),
                Toast.LENGTH_LONG
            ).show()
            navController(MainScreen)
        }


        SocketState.SuccesFinishedGame -> {
            navController(PodiumScreenESC)
            showConfetti = false
            isLoading = false
            ShowIntersticial = true
        }

        else -> {
            isLoading = false
        }
    }



    if (ShowIntersticial) {
        IntersticialComponent (IdsAdmob.ID_INSTERSTICIAL_FINISH_GAME) {
            if (it != null) {
                ShowIntersticial = false
                it.show(activity)
            } else {
                ShowIntersticial = false
            }
        }
    }

    val logo = painterResource(R.drawable.logo)

    Scaffold(
        Modifier.systemBarsPadding(),

        topBar = {
            TopBarNoTorch(
                logo,
                points = if (actualScore?.score != null) actualScore?.score.toString() else "0",
                playersNumber = roomData?.players?.size.toString()
            )
        },

        bottomBar = {
            BannerComponent(IdsAdmob.ID_BANNERS_QUIZ)
//            Row(
//                Modifier
//                    .fillMaxWidth()
//                    .height(80.dp)
//                    .padding(vertical = 5.dp, horizontal = 20.dp)
//            ) {
//                CustomButton(
//                    onClick = {
//                        socketVM.checkQuestion(selectedAnswer, actualPercent.toString())
//                              },
//                    colorShadow = Color.Transparent
//                ) {
//                    Text("Enviar", fontSize = 22.sp, fontWeight = FontWeight.Normal)
//                }
//            }
        }

    ) { paddingValues ->
        Column(
            Modifier
                .fillMaxSize()
                .background(BlackBackGround)
                .padding(paddingValues)
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            AnimatedVisibility(showAlertDialog) {
                AlertDialog(
                    onDismissRequest = { showAlertDialog = false },
                    title = { Text(LocalizedStringResource(R.string.confirm_exit_title), fontSize = 19.sp) },
                    text = {
                        Column {
                            Text(LocalizedStringResource(R.string.confirm_exit_msg))
                        }
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            showAlertDialog = false
                            socketVM.LeaveRoom()
                            navController(MainScreen)
                        }) {
                            Text(LocalizedStringResource(R.string.abandon))
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showAlertDialog = false }) {
                            Text(LocalizedStringResource(R.string.cancel))
                        }
                    }
                )



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
                            randomTip,
                            fontSize = 18.sp,
                            fontStyle = FontStyle.Italic,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(vertical = 20.dp)
                        )
                    }
                }
            } else if (showSuccessCheck) {

                LottieAnimationScore(R.raw.check)

            }else if(showErrorCheck){

                LottieAnimationScore(R.raw.error)

            }else if(showConfetti) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    Column (Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                        Text("Congratulations!", fontSize = 30.sp, color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.padding(30.dp))
                        Text(actualScore?.score.toString(), fontSize = 60.sp, color = MainOrange, fontWeight = FontWeight.SemiBold)
                    }
                    LottieAnimationScore(R.raw.confetti)

                }
            }else{

                Box(Modifier.weight(0.2f)) {
                    Column(
                        Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .height(30.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Box(
                                Modifier
                                    .weight(0.2f)
                                    .fillMaxHeight()
                            ) {
                                androidx.compose.animation.AnimatedVisibility(actualQuestion?.questionIndex != null) {
                                    Text(
                                        actualQuestion?.questionIndex.toString(),
                                        fontSize = 20.sp,
                                        color = Color.White
                                    )
                                }

                            }
                            Box(
                                Modifier
                                    .weight(0.5f)
                                    .fillMaxHeight()
                            ) {

                            }
                            Box(
                                Modifier
                                    .weight(0.3f)
                                    .fillMaxHeight()
                            ) {
                                Row(
                                    Modifier.fillMaxSize(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    Icon(Icons.Outlined.Timer, "ContIcon", tint = MainOrange)
                                    CountdownTimer(
                                        resetKey = actualQuestion?.questionIndex.toString(),
                                        totalMinutes = actualQuestion?.question?.time_limit?.toInt(),
                                        onChange = {
                                            actualPercent = it

                                        }) {
                                        isLoading = true
                                        //time finished
                                        socketVM.checkQuestion(
                                            selectedAnswer,
                                            percentTimeSelectedAnswer
                                        )
                                    }
                                }
                            }
                        }
                        LinearProgressIndicator(
                            modifier = Modifier.fillMaxWidth(),
                            progress = { actualPercent },
                            color = if (actualPercent < 0.4f) Color.Green else if (actualPercent >= 0.4f && actualPercent < 0.8f) MainOrange else Color.Red,
                            drawStopIndicator = {}
                        )
                    }
                }

                Box(
                    Modifier
                        .weight(0.8f)
                        .fillMaxWidth()
                ) {
                    Column(
                        Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        androidx.compose.animation.AnimatedVisibility(actualQuestion?.question?.text != null) {
                            Text(
                                actualQuestion?.question?.text.toString(),
                                fontSize = 18.sp,
                                color = Color.White,
                                modifier = Modifier.padding(bottom = 20.dp)
                            )
                        }


                        LazyColumn() {
                            itemsIndexed(
                                actualQuestion?.question?.answers ?: emptyList()
                            ) { index, answer ->

                                var letter = ""

                                when (index) {
                                    0 -> {
                                        letter = "A"
                                    }

                                    1 -> {
                                        letter = "B"
                                    }

                                    2 -> {
                                        letter = "C"
                                    }

                                    3 -> {
                                        letter = "D"
                                    }

                                    else -> {
                                        letter = ""
                                    }
                                }

                                CustomAnswerOption(
                                    selected = selectedAnswer == answer.answer_id,
                                    letterOption = letter,
                                    text = answer.text
                                ) {
                                    if (selectedAnswer != answer.answer_id) selectedAnswer =
                                        answer.answer_id else selectedAnswer = ""

                                }
                            }
                        }


                    }
                }
            }

        }
    }
}