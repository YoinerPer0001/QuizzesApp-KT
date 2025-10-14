package com.yoinerdev.quizzesia.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yoinerdev.linternapro.presenter.components.BannerComponent
import com.yoinerdev.quizzesia.IdsAdmob
import com.yoinerdev.quizzesia.R
import com.yoinerdev.quizzesia.core.alerts.SocketState
import com.yoinerdev.quizzesia.core.navegation.LobbyMultiplayerEsc
import com.yoinerdev.quizzesia.core.navegation.LobbyOtherPlayersESC
import com.yoinerdev.quizzesia.core.utils.LocalizedStringResource
import com.yoinerdev.quizzesia.data.dto.JoinRequest
import com.yoinerdev.quizzesia.presentation.components.CustomButton
import com.yoinerdev.quizzesia.presentation.components.CustomLoader
import com.yoinerdev.quizzesia.presentation.components.CustomTField
import com.yoinerdev.quizzesia.presentation.ui.theme.BlackBackGround
import com.yoinerdev.quizzesia.presentation.ui.theme.MainOrange
import com.yoinerdev.quizzesia.presentation.viewmodels.QuizzesSocketVM

@Composable
fun JoinGameScreen(
    socketVM:QuizzesSocketVM,
    navController:(Any)->Unit
){

    val logo = painterResource(R.drawable.playicon)

    val roomData by socketVM.roomData.collectAsState()
    var codeValue by remember { mutableStateOf("") }

    LaunchedEffect  (Unit){
        socketVM.onCharge()
        socketVM.onPlayerJoin()
    }
    val context = LocalContext.current
    val state by socketVM.state.collectAsState()
    var isLoading by remember { mutableStateOf(false) }

    when(state){
        SocketState.IsLoading -> {isLoading = true}
        SocketState.ErrorToJoin -> {
            Toast.makeText(context, LocalizedStringResource(R.string.error_invalid_code), Toast.LENGTH_SHORT).show()
            isLoading =false
        }
        SocketState.SuccessJoined -> {
            navController(LobbyOtherPlayersESC)
            Toast.makeText(context, LocalizedStringResource(R.string.success_joined), Toast.LENGTH_SHORT).show()
            isLoading =false
        }
        else -> {
            isLoading =false
        }
    }



    Scaffold(
        Modifier.systemBarsPadding(),
        bottomBar = {
            BannerComponent(IdsAdmob.ID_BANNERS_JOIN)
        },
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
                            navController(com.yoinerdev.quizzesia.core.navegation.MainScreen)
                            socketVM.disconnect()
                        }
                        .size(25.dp)
                )
            }
        }
    ) { paddingValues ->

        if(isLoading){
            CustomLoader(
                text = LocalizedStringResource(R.string.joining_room)
            )
        }else{


        Column(
            Modifier
                .fillMaxSize()
                .background(BlackBackGround)
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
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
                LocalizedStringResource(R.string.enter_room_code),
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 10.dp)
            )

            Box(Modifier.fillMaxWidth().padding(horizontal = 20.dp)){
                CustomTField(
                    value = codeValue,
                    onValueChange = {codeValue = it},
                    label = LocalizedStringResource(R.string.code),
                    leadingIcon = Icons.Default.Lock
                )
            }

            Box(Modifier.padding(horizontal = 20.dp)){
                Text(
                    LocalizedStringResource(R.string.join_room_info),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 20.dp)
                )
            }

            Box(Modifier.padding(horizontal = 20.dp)){
                CustomButton(
                    onClick = {
                        socketVM.JoinToRoom(code = codeValue)
                    },
                    width = 0.5f
                ) {
                    Row (Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                        Icon(Icons.Default.PlayCircle, "iconPlay", tint = Color.White)
                        Text(
                            LocalizedStringResource(R.string.join),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 5.dp)
                        )
                    }
                }
            }



        }}
    }

}