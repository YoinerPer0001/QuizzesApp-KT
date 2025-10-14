package com.yoinerdev.quizzesia.presentation.screens


import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yoinerdev.quizzesia.presentation.ui.theme.BlackBackGround
import com.yoinerdev.quizzesia.presentation.viewmodels.QuizzesSocketVM
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.zIndex
import com.yoinerdev.quizzesia.R
import com.yoinerdev.quizzesia.core.alerts.SocketState
import com.yoinerdev.quizzesia.core.navegation.MainScreen
import com.yoinerdev.quizzesia.core.utils.LocalizedStringResource
import com.yoinerdev.quizzesia.data.dto.Players
import com.yoinerdev.quizzesia.presentation.components.CustomChipPlayers
import com.yoinerdev.quizzesia.presentation.components.CustomChipPlayersPodium
import com.yoinerdev.quizzesia.presentation.components.animations.LottieAnimationScore
import com.yoinerdev.quizzesia.presentation.ui.theme.BronzeColor
import com.yoinerdev.quizzesia.presentation.ui.theme.GoldColor
import com.yoinerdev.quizzesia.presentation.ui.theme.MainOrange
import com.yoinerdev.quizzesia.presentation.ui.theme.SilverColor

@Composable
fun PodiumScreen(
    socketVM: QuizzesSocketVM,
    navController: (Any) -> Unit
) {
    BackHandler {
        navController(MainScreen)
    }
    val actualQuestion by socketVM.actualQuestions.collectAsState()
    val actualScore by socketVM.userScore.collectAsState()
    val roomData by socketVM.roomData.collectAsState()
    val state by socketVM.state.collectAsState()
    val playerLeave by socketVM.playerLeave.collectAsState()
    val context = LocalContext.current
    val podiumPlayers = roomData?.players
        ?.sortedByDescending { it.score } // 🔹 de mayor a menor puntaje
        ?.take(3)
        ?.let {
            if (it.size == 3) listOf(it[1], it[0], it[2]) else if (it.size == 2) listOf(
                it[1],
                it[0]
            ) else listOf(it[0])
        }

    when (state) {

        SocketState.HostLeave -> {
            Toast.makeText(
                context,
                LocalizedStringResource(R.string.host_left),
                Toast.LENGTH_SHORT
            ).show()
            navController(com.yoinerdev.quizzesia.core.navegation.JoinGameScreen)
        }

        SocketState.PlayerLeave -> {
            Toast.makeText(
                context,
                "$playerLeave ${LocalizedStringResource(R.string.player_left)}",
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
                            navController(MainScreen)
                        }
                        .size(25.dp)
                )
                Row(
                    Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                }
            }
        }
    ) { paddingValues ->
        Column(
            Modifier
                .fillMaxSize()
                .background(BlackBackGround)
                .padding(paddingValues)
                .padding(bottom = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            if (!roomData?.players.isNullOrEmpty()) {
                Box(
                    Modifier
                        .weight(0.5f)
                        .fillMaxWidth()
                ) {
                    Row(
                        Modifier
                            .fillMaxSize()
                            .padding(horizontal = 20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        val maxPercent = roomData?.players?.firstOrNull()?.score ?: 0


                        podiumPlayers?.forEachIndexed { index, playerPodium ->

                            val colorContainer = when (index) {
                                0 -> MainOrange
                                1 -> SilverColor
                                2 -> BronzeColor
                                else -> Color.Gray // opcional
                            }

                            val medall = when (index) {
                                1 -> "\uD83E\uDD47" // 🥇
                                0 -> {
                                    if (podiumPlayers.size >= 2) "\uD83E\uDD48" else "\uD83E\uDD47"
                                } // 🥈
                                2 -> "\uD83E\uDD49" // 🥉
                                else -> ""
                            }
                            val boxHeight = if (maxPercent > 0) {
                                playerPodium.score.toFloat() / maxPercent.toFloat()
                            } else {
                                0f
                            }
                            Box(
                                Modifier
                                    .weight(0.2f)
                                    .fillMaxHeight()
                                    .padding(horizontal = 10.dp),
                                contentAlignment = Alignment.BottomCenter
                            ) {
                                //barra

                                Column(
                                    Modifier.fillMaxWidth(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Box(
                                        Modifier
                                            .size(50.dp)
                                            .clip(CircleShape)
                                            .background(MainOrange),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            playerPodium.name.replaceFirstChar { it.uppercase() }
                                                .firstOrNull().toString(),
                                            fontSize = 30.sp,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }
                                    Text(
                                        playerPodium.name?.split(" ")
                                            ?.filter { it.isNotEmpty() } // evita strings vacíos por el split("")
                                            ?.let { parts ->
                                                val first = parts.getOrNull(0)?.lowercase()
                                                    ?.replaceFirstChar { it.uppercase() } ?: ""
                                                first
                                            } ?: "",
                                        fontSize = 18.sp,
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis,
                                        textAlign = TextAlign.Center,
                                        color = Color.White,
                                    )
                                    Text(
                                        playerPodium.score.toString(),
                                        fontSize = 28.sp,
                                        maxLines = 2,
                                        fontWeight = FontWeight.Bold,
                                        overflow = TextOverflow.Ellipsis,
                                        textAlign = TextAlign.Center,

                                        color = MainOrange
                                    )

                                    PodiumElement(boxHeight, colorContainer, medall)
                                }

                            }
                        }
                    }
                    Box(Modifier.offset(y = 150.dp)) {
                        LottieAnimationScore(R.raw.stars, size = 1f)
                    }
                }





                Box(
                    Modifier
                        .weight(0.5f)
                        .padding(top = 80.dp)
                ) {
                    LazyColumn {
                        itemsIndexed(roomData?.players ?: emptyList()) { position, player ->
                            if ((position + 1) > 3) {
                                CustomChipPlayersPodium(
                                    player,
                                    position = position.toString()
                                )
                            }


                        }

                        if (roomData?.players?.size!! <= 3) {
                            item {
                                Text(
                                    text = LocalizedStringResource(R.string.no_more_participants),
                                    fontSize = 25.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.White
                                )

                            }
                        }
                    }
                }
            }


        }
    }

}