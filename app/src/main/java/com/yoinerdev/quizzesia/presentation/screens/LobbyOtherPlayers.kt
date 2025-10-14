package com.yoinerdev.quizzesia.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yoinerdev.quizzesia.R
import com.yoinerdev.quizzesia.core.alerts.SocketState
import com.yoinerdev.quizzesia.core.navegation.JoinGameScreen
import com.yoinerdev.quizzesia.core.navegation.MainScreen
import com.yoinerdev.quizzesia.core.navegation.QuizScreenESC
import com.yoinerdev.quizzesia.core.utils.LocalizedStringResource
import com.yoinerdev.quizzesia.presentation.components.CustomButton
import com.yoinerdev.quizzesia.presentation.components.CustomChipPlayers
import com.yoinerdev.quizzesia.presentation.components.CustomOutlineButton
import com.yoinerdev.quizzesia.presentation.ui.theme.BlackBackGround
import com.yoinerdev.quizzesia.presentation.ui.theme.BlueCake
import com.yoinerdev.quizzesia.presentation.ui.theme.MainOrange
import com.yoinerdev.quizzesia.presentation.ui.theme.OrangeTransparent
import com.yoinerdev.quizzesia.presentation.ui.theme.SecondaryGray
import com.yoinerdev.quizzesia.presentation.ui.theme.SecondaryGrayTransparent
import com.yoinerdev.quizzesia.presentation.ui.theme.SemiTransparent
import com.yoinerdev.quizzesia.presentation.viewmodels.QuizzesSocketVM

@Composable
fun LobbyOtherPlayers(
    socketVM: QuizzesSocketVM,
    navController:(Any)->Unit,
){
    val roomData by socketVM.roomData.collectAsState()
    val context = LocalContext.current
    val playerLeave by socketVM.playerLeave.collectAsState()
    val state by socketVM.state.collectAsState()

    LaunchedEffect (Unit){
        socketVM.onGameStarted()
        socketVM.onPlayerLeaveRoom()
    }

    when (state){
        SocketState.HostLeave -> {
            navController(MainScreen)
            Toast.makeText(context, "$playerLeave + ${LocalizedStringResource(R.string.player_left)}", Toast.LENGTH_SHORT).show()
        }
        SocketState.PlayerLeave -> {
            Toast.makeText(context, "$playerLeave + ${LocalizedStringResource(R.string.player_left)}", Toast.LENGTH_SHORT).show()
        }
        SocketState.InsParticipants -> {
            Toast.makeText(context, LocalizedStringResource(R.string.insufficient_participants), Toast.LENGTH_LONG).show()
            navController(JoinGameScreen)
        }
        SocketState.SuccessStartGame -> {
            navController(QuizScreenESC)
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
                            navController(JoinGameScreen)
                        }
                        .size(25.dp)
                )
                Row (Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically){
                    Text("Lobby", fontSize = 22.sp, color = Color.White, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding())
                }
            }
        }
    ) { paddingValues ->
        Column(
            Modifier
                .fillMaxSize()
                .background(BlackBackGround)
                .padding(paddingValues).padding(bottom = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Box(Modifier.weight(0.2f)){
                Column (Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(LocalizedStringResource(R.string.waiting_players), fontSize = 32.sp, color = Color.White, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding())
                    Text(LocalizedStringResource(R.string.quiz_starting_soon), fontSize = 22.sp, color = Color.Gray, fontWeight = FontWeight.Normal, modifier = Modifier.padding(vertical = 10.dp))
                }
            }
            Box(Modifier.weight(0.8f).fillMaxWidth()){

                LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                    items(roomData?.players ?: emptyList()){ player ->
                        CustomChipPlayers(
                            player = player,
                            hostId = roomData?.hostId ?: ""
                        )
                    }

                }

            }
            Box(Modifier.weight(0.12f).fillMaxWidth().padding(horizontal = 20.dp)){
               Column (Modifier.fillMaxSize().padding(vertical = 5.dp), verticalArrangement = Arrangement.Center) {
                   CustomButton (
                       onClick = {
                           socketVM.LeaveRoom()
                       },
                       contentColor = MainOrange,
                       colorShadow = Color.Transparent
                   ) {
                       Text(LocalizedStringResource(R.string.leave_room), fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                   }
               }
            }

        }}


}