package com.yoinerdev.quizzesia.presentation.screens

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.PeopleAlt
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.yoinerdev.quizzesia.R
import com.yoinerdev.quizzesia.core.alerts.SocketState
import com.yoinerdev.quizzesia.core.navegation.ModesQuizScreen
import com.yoinerdev.quizzesia.core.navegation.QuizScreenESC
import com.yoinerdev.quizzesia.core.utils.LocalizedStringResource
import com.yoinerdev.quizzesia.presentation.components.BottomBarNav
import com.yoinerdev.quizzesia.presentation.components.CustomButton
import com.yoinerdev.quizzesia.presentation.components.CustomChipPlayers
import com.yoinerdev.quizzesia.presentation.components.CustomSugestionChip
import com.yoinerdev.quizzesia.presentation.components.TopBarMain
import com.yoinerdev.quizzesia.presentation.ui.theme.BlackBackGround
import com.yoinerdev.quizzesia.presentation.ui.theme.BlueCake
import com.yoinerdev.quizzesia.presentation.ui.theme.DarkSkyBlue
import com.yoinerdev.quizzesia.presentation.ui.theme.MainOrange
import com.yoinerdev.quizzesia.presentation.ui.theme.OrangeTransparent
import com.yoinerdev.quizzesia.presentation.ui.theme.SecondaryGray
import com.yoinerdev.quizzesia.presentation.ui.theme.SkyBlueCake
import com.yoinerdev.quizzesia.presentation.ui.theme.SkyBlueCakeTransparent
import com.yoinerdev.quizzesia.presentation.viewmodels.QuizzesSocketVM

@Composable
fun LobbyMultiplayerScreen(
    quizId: String,
    socketVM: QuizzesSocketVM,
    navController: (Any) -> Unit,
) {

    val logo = painterResource(R.drawable.playicon)

    val roomData by socketVM.roomData.collectAsState()
    val context = LocalContext.current

    val roomCode = roomData?.roomCode ?: "—"
    val playerLeave by socketVM.playerLeave.collectAsState()
    
    val state by socketVM.state.collectAsState()

    val message = """ ${LocalizedStringResource(R.string.share_quiz_message1)} + $roomCode + ${LocalizedStringResource(R.string.share_quiz_message1)}
    """.trimIndent()

    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, message)
        type = "text/plain"
    }

    val activity = (context as Activity)
    
    LaunchedEffect (Unit){
        socketVM.onGameStarted()
        socketVM.onPlayerLeaveRoom()
    }
    
    when (state){

        SocketState.PlayerLeave -> {
            Toast.makeText(context, "$playerLeave + ${LocalizedStringResource(R.string.player_left)}", Toast.LENGTH_SHORT).show()
        }

        SocketState.InsParticipants -> {
            Toast.makeText(
                context,
                LocalizedStringResource(R.string.insufficient_participants),
                Toast.LENGTH_LONG
            ).show()
        }

        SocketState.SuccessStartGame -> {
            navController(QuizScreenESC)
        }

        SocketState.ErrorStartGame -> {
            Toast.makeText(
                context,
                LocalizedStringResource(R.string.error_start_game),
                Toast.LENGTH_SHORT
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
                    .padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    Icons.Default.ArrowBackIosNew,
                    "iconBack",
                    tint = Color.White,
                    modifier = Modifier
                        .clickable {
                            socketVM.LeaveRoom()
                            navController(ModesQuizScreen(quizId))
                        }
                        .size(25.dp)
                )
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
            Box(Modifier.weight(0.6f)) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(horizontal = 30.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(Modifier) {
                        Image(
                            logo,
                            "logoPlay",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.size(250.dp, 200.dp)
                        )
                    }
                    Text(
                        text = LocalizedStringResource(R.string.enter_room_code),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )

                    ElevatedCard(
                        onClick = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp),
                        shape = CircleShape,
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 10.dp
                        ),
                        colors = CardDefaults.cardColors(
                            containerColor = SkyBlueCake
                        )
                    ) {
                        Row(Modifier.fillMaxSize()) {
                            Box(
                                modifier = Modifier
                                    .weight(0.8f)
                                    .fillMaxHeight(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    roomData?.roomCode.toString(),
                                    fontSize = 50.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                            VerticalDivider(
                                color = Color.White,
                                modifier = Modifier.padding(vertical = 1.dp)
                            )
                            Box(
                                modifier = Modifier
                                    .weight(0.25f)
                                    .fillMaxHeight()
                                    .clickable {
                                        activity.startActivity(sendIntent)
                                    }
                                    .background(SkyBlueCakeTransparent)
                            ) {
                                Row(
                                    Modifier.fillMaxSize(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Icon(
                                        Icons.Default.Share,
                                        "iconShare",
                                        tint = Color.White,
                                        modifier = Modifier.size(50.dp)
                                    )
                                }

                            }

                        }

                    }

                    Text(
                        LocalizedStringResource(R.string.share_code_info),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(vertical = 20.dp)
                    )
                }
            }

            //players zone
            Box(
                Modifier
                    .weight(0.5f)
                    .clip(shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                    .background(SecondaryGray)
            ) {
                Column(Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(80.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Row(
                            Modifier
                                .weight(0.5f)
                                .height(60.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Box(
                                Modifier
                                    .size(60.dp)
                                    .clip(RoundedCornerShape(topStart = 10.dp, bottomStart = 10.dp))
                                    .background(
                                        SkyBlueCake
                                    ), contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    roomData?.players?.size.toString(),
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                            Box(
                                Modifier
                                    .size(60.dp)
                                    .clip(RoundedCornerShape(topEnd = 10.dp, bottomEnd = 10.dp))
                                    .background(SkyBlueCake), contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Default.PeopleAlt,
                                    "iconShare",
                                    tint = Color.White,
                                    modifier = Modifier.size(40.dp)
                                )
                            }
                        }

                        Box(Modifier.weight(0.4f)) {
                            CustomButton(
                                onClick = {
                                    socketVM.startGame()
                                },
                                width = 0.8f
                            ) {
                                Row(
                                    Modifier.fillMaxSize(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Icon(
                                        Icons.Default.PlayCircle,
                                        "iconShare",
                                        tint = Color.White,
                                        modifier = Modifier.size(40.dp)
                                    )
                                    Text(LocalizedStringResource(R.string.start), modifier = Modifier.padding(horizontal = 5.dp))
                                }
                            }
                        }

                    }
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2)) {

                        items(roomData?.players ?: emptyList()) { player ->
                            AnimatedVisibility(true) {
                                CustomChipPlayers(
                                    player = player,
                                    hostId = roomData?.hostId ?: "",
                                    invertColor = true
                                )
                            }

                        }
                    }
                }
            }
        }
    }

}