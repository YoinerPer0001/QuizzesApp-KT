package com.yoinerdev.quizzesia.presentation.screens

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsIgnoringVisibility
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.PictureAsPdf
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.yoinerdev.linternapro.presenter.components.BannerComponent
import com.yoinerdev.linternapro.presenter.components.IntersticialComponent
import com.yoinerdev.linternapro.presenter.components.IntersticialComponentRecompensados
import com.yoinerdev.quizzesia.IdsAdmob
import com.yoinerdev.quizzesia.R
import com.yoinerdev.quizzesia.core.navegation.CreateQuizzesScreen
import com.yoinerdev.quizzesia.core.navegation.JoinGameScreen
import com.yoinerdev.quizzesia.core.navegation.LobbyMultiplayerEsc
import com.yoinerdev.quizzesia.core.navegation.LobbyOtherPlayersESC
import com.yoinerdev.quizzesia.core.navegation.LoginScreen
import com.yoinerdev.quizzesia.core.navegation.ModesQuizScreen
import com.yoinerdev.quizzesia.core.navegation.PodiumScreenESC
import com.yoinerdev.quizzesia.core.navegation.QuizScreenESC
import com.yoinerdev.quizzesia.core.utils.LocalizedStringResource
import com.yoinerdev.quizzesia.presentation.components.BottomBarNav
import com.yoinerdev.quizzesia.presentation.components.CardMain
import com.yoinerdev.quizzesia.presentation.components.CustomCardModes
import com.yoinerdev.quizzesia.presentation.components.RotatingLoadingImage
import com.yoinerdev.quizzesia.presentation.components.TopBarMain
import com.yoinerdev.quizzesia.presentation.ui.theme.BlackBackGround
import com.yoinerdev.quizzesia.presentation.ui.theme.BlueCake
import com.yoinerdev.quizzesia.presentation.ui.theme.MainOrange
import com.yoinerdev.quizzesia.presentation.ui.theme.SecondaryGray
import com.yoinerdev.quizzesia.presentation.viewmodels.AuthViewModel

@OptIn(ExperimentalLayoutApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    viewModel: AuthViewModel,
    navController: (destination: Any) -> Unit
) {
    val context = LocalContext.current
    val user by viewModel.user.collectAsState()
    val logo = painterResource(R.drawable.logo)
    val pdfIcon = painterResource(R.drawable.pdf_icon)
    val attemptsRemaining by viewModel.attempsRemaining.collectAsState()
    var showDialogQuota by remember { mutableStateOf(false) }
    val joinImage = painterResource(R.drawable.joincuted)

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val uri = result.data?.data
            uri?.let {
                Log.d("PDF INFO", it.toString())
                navController(CreateQuizzesScreen(0, uri.toString()))
            }
        }
    }

    var showDialogAddAttempt by remember { mutableStateOf(false) }
    var ShowIntersticial by remember { mutableStateOf(false) }
    val activity = context as Activity
    var showDialogNoAdds by remember { mutableStateOf(false) }

    LaunchedEffect(user) {
        if (user == null) {
            navController(LoginScreen)
        }
    }

    LaunchedEffect(attemptsRemaining) {
        viewModel.getuserAttempts()
    }

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
        bottomBar = {

            BottomBarNav(user = user, btnSelected = 0) {
                navController(it)
            }

        },

        topBar = {
            TopBarMain(logo, attemptsRemaining){
                showDialogAddAttempt = true
            }
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

            AnimatedVisibility(showDialogQuota) {
                AlertDialog(
                    onDismissRequest = { showDialogQuota = false },
                    title = { Text(text = "✨ ¡Consigue más créditos! ✨", fontSize = 19.sp) },
                    text = {
                        Column {
                            Text("▶️ Mira un video corto y recibe 🎁 créditos extra para seguir creando quizzes sin interrupciones.")
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("⏳ O espera 24 horas para que tus créditos se renueven automáticamente.")
                        }
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            ShowIntersticial = true
                            showDialogQuota = false }
                        ) {
                            Text("👌 Entendido")
                        }
                    }
                )

            }

            AnimatedVisibility(showDialogAddAttempt) {
                AlertDialog(
                    onDismissRequest = { showDialogAddAttempt = false },
                    title = { Text(text = "🎬 ¡Gana más créditos viendo un anuncio!", fontSize = 19.sp) },
                    text = {
                        Column {
                            Text("💡 Mira un video corto de un anuncio y recibe 🎁 créditos adicionales para seguir creando quizzes sin pausas.")
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("⏳ También puedes esperar 24 horas para que tus créditos se renueven automáticamente.")
                        }
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            // Acción para reproducir el anuncio
                            ShowIntersticial = true
                            showDialogAddAttempt = false
                        }) {
                            Text("▶️ Ver anuncio")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDialogAddAttempt = false }) {
                            Text("Cancelar")
                        }
                    }
                )
            }

            AnimatedVisibility(showDialogNoAdds) {
                AlertDialog(
                    onDismissRequest = { showDialogNoAdds = false },
                    title = { Text(text = "🚫 No hay anuncios disponibles", fontSize = 19.sp) },
                    text = {
                        Column {
                            Text("😕 En este momento no hay videos disponibles para obtener créditos adicionales.")
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("⏳ Inténtalo nuevamente en unos minutos o espera 24 horas para que tus créditos se renueven automáticamente.")
                        }
                    },
                    confirmButton = {
                        TextButton(onClick = { showDialogNoAdds = false }) {
                            Text("👌 Entendido")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDialogAddAttempt = true }) {
                            Text("Reintentar")
                        }
                    }
                )

            }


            Box(
                Modifier
                    .weight(0.2f)
                    .fillMaxWidth(), contentAlignment = Alignment.Center
            ) {
                Text(LocalizedStringResource(R.string.manTopMessage), fontSize = 25.sp, color = Color.White)
            }

            Row(Modifier.fillMaxWidth().weight(0.3f)) {


                Box(Modifier.weight(0.5f), contentAlignment = Alignment.Center) {
                    CardMain(
                        icon = {
                            Icon(
                                pdfIcon,
                                contentDescription = "iconPdf",
                                modifier = Modifier.size(80.dp),
                                tint = Color.White
                            )
                        },
                        text = LocalizedStringResource(R.string.main_Upload_PDF)
                    ) {
                        //SEND TO CREATE QUIZ SCREEN
//                    navController(CreateQuizzesScreen(0))
                        attemptsRemaining?.attempts_remaining?.let {
                            if (it > 0) {
                                val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                                    addCategory(Intent.CATEGORY_OPENABLE)
                                    type = "application/pdf"
                                }
                                launcher.launch(intent)
                            } else {
                                showDialogQuota = true
                            }
                        }


                    }
                }
                Box(Modifier.weight(0.5f), contentAlignment = Alignment.TopCenter) {
                    CardMain(
                        icon = {
                            Icon(
                                Icons.Outlined.EditNote,
                                contentDescription = "iconPdf",
                                modifier = Modifier.size(
                                    80.dp
                                ),
                                tint = Color.White
                            )
                        },
                        text = LocalizedStringResource(R.string.mainWrite_topic),
                        color = MainOrange
                    ) {
                        attemptsRemaining?.attempts_remaining?.let {
                            if (it > 0) {
                                //SEND TO CREATE QUIZ SCREEN
                                navController(CreateQuizzesScreen(1, null))
                            } else {
                                showDialogQuota = true
                            }
                        }

                    }
                }

            }
            Box(Modifier.weight(0.4f).padding(horizontal = 20.dp)){
                Column (Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceAround, horizontalAlignment = Alignment.CenterHorizontally){
                    CustomCardModes(
                        joinImage,
                        LocalizedStringResource(R.string.join_with_code),
                        LocalizedStringResource(R.string.join_with_code_desc)
                    ) {
                        navController(JoinGameScreen)
                    }

                    //ads

                    BannerComponent(IdsAdmob.ID_BANNER_MAIN)
                }
            }

        }
    }
}