package com.yoinerdev.quizzesia.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yoinerdev.quizzesia.R
import com.yoinerdev.quizzesia.core.alerts.QuizzesAlerts
import com.yoinerdev.quizzesia.core.navegation.MyQuizzesEsc
import com.yoinerdev.quizzesia.core.utils.LocalizedStringResource
import com.yoinerdev.quizzesia.core.utils.formatDate
import com.yoinerdev.quizzesia.presentation.components.BottomBarNav
import com.yoinerdev.quizzesia.presentation.components.TopBarMain
import com.yoinerdev.quizzesia.presentation.ui.theme.BlackBackGround
import com.yoinerdev.quizzesia.presentation.viewmodels.AuthViewModel
import com.yoinerdev.quizzesia.presentation.viewmodels.QuizzesVM

@Composable
fun AttemptsScreen(
    quizId:String,
    quizTitle:String,
    quizVM:QuizzesVM,
    viewModel: AuthViewModel,
    navController: (Any) -> Unit
) {
    val attempts by quizVM.attempts.collectAsState()
    val state by quizVM.state.collectAsState()
    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current

    when (state) {

        QuizzesAlerts.IsLoading -> {
            isLoading = true
        }

        QuizzesAlerts.SuccessToGetQuizzesUser -> {
            isLoading = false

        }

        QuizzesAlerts.ErrorTogetAttemptsQuizz -> {
            isLoading = false
            Toast.makeText(context, LocalizedStringResource(R.string.error_fetch_data), Toast.LENGTH_SHORT)
                .show()
            navController(MyQuizzesEsc)
        }

        else -> {
            isLoading = false
        }
    }

    LaunchedEffect (Unit) {
        quizVM.getUserAttempts(quizId)
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
                            navController(MyQuizzesEsc)
                        }
                        .size(25.dp)
                )
                Row(
                    Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(quizTitle, fontSize = 25.sp, color = Color.White, fontWeight = FontWeight.SemiBold, maxLines = 1, overflow = TextOverflow.Ellipsis)

                }
            }
        }

    ) { paddingValues ->


        Column(
            Modifier
                .fillMaxSize()
                .background(BlackBackGround)
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                itemsIndexed(attempts ?: emptyList()){ index , attempt ->
                    ElevatedCard(
                        onClick = {},
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 5.dp
                        ),
                        modifier = Modifier.fillMaxWidth().padding(10.dp)
                    ) {
                        Column (Modifier.fillMaxSize().padding(10.dp)) {
                            Text(LocalizedStringResource(R.string.score) +":", fontSize = 18.sp, color = Color.Black, fontWeight = FontWeight.SemiBold)
                            Text(attempt.score.toString(), fontSize = 35.sp, color = Color.Black, fontWeight = FontWeight.SemiBold)
                            Row (Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End){
                                Text(formatDate(attempt.createdAt), fontSize = 15.sp, color = Color.DarkGray, fontWeight = FontWeight.Normal, fontStyle = FontStyle.Italic)
                            }
                        }
                    }
                }
            }



        }
    }

}