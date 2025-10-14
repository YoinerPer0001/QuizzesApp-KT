package com.yoinerdev.quizzesia.presentation.screens

import android.app.Activity
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yoinerdev.linternapro.presenter.components.BannerComponent
import com.yoinerdev.linternapro.presenter.components.IntersticialComponentRecompensados
import com.yoinerdev.quizzesia.IdsAdmob
import com.yoinerdev.quizzesia.R
import com.yoinerdev.quizzesia.core.alerts.QuizzesAlerts
import com.yoinerdev.quizzesia.core.helpers.getSavedLanguage
import com.yoinerdev.quizzesia.core.navegation.AttemptsScreenESC
import com.yoinerdev.quizzesia.core.navegation.ModesQuizScreen
import com.yoinerdev.quizzesia.core.utils.LocalizedStringResource
import com.yoinerdev.quizzesia.core.utils.formatDate
import com.yoinerdev.quizzesia.presentation.components.BottomBarNav
import com.yoinerdev.quizzesia.presentation.components.CardEstatistics
import com.yoinerdev.quizzesia.presentation.components.CustomAlertDialog
import com.yoinerdev.quizzesia.presentation.components.CustomButton
import com.yoinerdev.quizzesia.presentation.components.CustomFilterChip
import com.yoinerdev.quizzesia.presentation.components.CustomOutlineButton
import com.yoinerdev.quizzesia.presentation.components.CustomSugestionChip
import com.yoinerdev.quizzesia.presentation.components.RotatingLoadingImage
import com.yoinerdev.quizzesia.presentation.components.TopBarMain
import com.yoinerdev.quizzesia.presentation.ui.theme.BlackBackGround
import com.yoinerdev.quizzesia.presentation.ui.theme.BlueCake
import com.yoinerdev.quizzesia.presentation.ui.theme.GreenCakeTransparent
import com.yoinerdev.quizzesia.presentation.ui.theme.MainOrange
import com.yoinerdev.quizzesia.presentation.ui.theme.SecondaryGray
import com.yoinerdev.quizzesia.presentation.ui.theme.SecondaryGrayTransparent
import com.yoinerdev.quizzesia.presentation.ui.theme.SemiTransparent
import com.yoinerdev.quizzesia.presentation.ui.theme.YellowCake
import com.yoinerdev.quizzesia.presentation.viewmodels.AuthViewModel
import com.yoinerdev.quizzesia.presentation.viewmodels.QuizzesVM
import java.util.Locale

@Composable
fun MyQuizzesScreen(
    viewModel: AuthViewModel,
    quizzesVM: QuizzesVM,
    navController: (Any) -> Unit,
) {
    val logo = painterResource(R.drawable.logo)
    val user by viewModel.user.collectAsState()
    val attemptsRemaining by viewModel.attempsRemaining.collectAsState()
    var chipSelected by remember { mutableStateOf("") }
    val context = LocalContext.current
    val myQuizzes by quizzesVM.userQuizzes.collectAsState()

    val categories by quizzesVM.categoriesList.collectAsState()

    var isLoading by remember { mutableStateOf(false) }

    val state by quizzesVM.state.collectAsState()
    val notFoundImage = painterResource(R.drawable.not_found)

    var page by remember { mutableIntStateOf(1) }

    var selectedQuiz by remember { mutableStateOf("") }

    when (state) {

        QuizzesAlerts.IsLoading -> {
            isLoading = true
        }

        QuizzesAlerts.ErrorToGetQuizzesUser -> {
            quizzesVM.getUserQuizzes(page, null)
            Toast.makeText(
                context,
                LocalizedStringResource(R.string.error_fetch_quizzes),
                Toast.LENGTH_SHORT
            ).show()

            isLoading = true
        }

        QuizzesAlerts.SuccessToGetQuizzesUser -> {
            isLoading = false
        }

        else -> {
            isLoading = false
        }
    }

    var showDialogAddAttempt by remember { mutableStateOf(false) }
    var ShowIntersticial by remember { mutableStateOf(false) }
    val activity = context as Activity
    var showDialogNoAdds by remember { mutableStateOf(false) }
    var showDialogQuota by remember { mutableStateOf(false) }

    LaunchedEffect(chipSelected, page) {
        quizzesVM.getUserQuizzes(page, chipSelected)
    }

    LaunchedEffect(Unit) {
        quizzesVM.getUserQuizzes(page, null)
        getSavedLanguage(context).collect {
            quizzesVM.getAllCategories(it)
        }
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

            BottomBarNav(user = user, btnSelected = 1) {
                navController(it)
            }

        },

        topBar = {
            TopBarMain(
                logo,
                attemptsRemaining,
                text = LocalizedStringResource(R.string.my_quizzes),
                clipSize = 0f
            ) {
                showDialogAddAttempt = true
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
            AnimatedVisibility(showDialogQuota) {
                AlertDialog(
                    onDismissRequest = { showDialogQuota = false },
                    title = { Text(text = LocalizedStringResource(R.string.get_more_credits_title), fontSize = 19.sp) },
                    text = {
                        Column {
                            Text(LocalizedStringResource(R.string.watch_video_earn_credits))
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(LocalizedStringResource(R.string.wait_credits_renew))
                        }
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            ShowIntersticial = true
                            showDialogQuota = false
                        }) {
                            Text(LocalizedStringResource(R.string.understood))
                        }
                    }
                )

            }
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
                modifier = Modifier
                    .weight(0.07f)
                    .fillMaxWidth()
            ) {
                Column(Modifier.fillMaxSize()) {
//                    Row(Modifier.padding(5.dp), verticalAlignment = Alignment.CenterVertically) {
//                        Icon(Icons.Filled.Category, "iconCat")
//                        Text(
//                            "Filtrar por categoria",
//                            modifier = Modifier.padding(horizontal = 5.dp),
//                            fontSize = 18.sp
//                        )
//                    }
                    LazyHorizontalGrid(rows = GridCells.Fixed(1)) {
                        items(categories) { cat ->
                            Box (modifier = Modifier.height(50.dp)) {
                                CustomFilterChip(cat.second, chipSelected == cat.first, cat.first) {
                                    if (chipSelected != it) {
                                        chipSelected = it
                                    } else {
                                        chipSelected = ""
                                    }
                                }
                            }
                        }
                    }
                    HorizontalDivider(Modifier.padding(), color = Color.LightGray)
                }

            }
            if (isLoading) {
                Box(Modifier.weight(0.9f)) {
                    Column(
                        Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        RotatingLoadingImage(stop = false)
                        Text(
                            text = LocalizedStringResource(R.string.loading_quizzes),
                            fontSize = 18.sp,
                            modifier = Modifier.padding(vertical = 10.dp),
                            color = Color.White
                        )

                    }
                }
            } else {

                Box(
                    modifier = Modifier
                        .weight(0.9f)
                        .padding(top = 20.dp)
                ) {
                    Column {
                        LazyColumn(contentPadding = PaddingValues(horizontal = 10.dp)) {


                            itemsIndexed(myQuizzes?.data?.quizzes ?: emptyList()) { index, quiz ->

                                Column {
                                    ElevatedCard(
                                        colors = CardDefaults.cardColors(
                                            containerColor = SecondaryGray
                                        ),
                                        onClick = {
                                            if(selectedQuiz != quiz.quiz_id){
                                                selectedQuiz = quiz.quiz_id
                                            }else{
                                                selectedQuiz = ""
                                            }
                                        },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(120.dp)
                                            .padding(top = 10.dp),
                                        shape = RoundedCornerShape(
                                            topStart = 10.dp,
                                            topEnd = 10.dp,
                                            bottomEnd = if (selectedQuiz != quiz.quiz_id)  10.dp else 0.dp,
                                            bottomStart = if (selectedQuiz != quiz.quiz_id)  10.dp else 0.dp),
                                    ) {
                                        Row(Modifier.fillMaxSize()) {
                                            Box(
                                                Modifier
                                                    .weight(0.2f)
                                                    .fillMaxHeight(),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Image(logo, "logo", modifier = Modifier.size(50.dp))
                                            }
                                            Box(
                                                Modifier
                                                    .weight(0.8f)
                                                    .fillMaxHeight()
                                            ) {
                                                Column(
                                                    Modifier
                                                        .fillMaxSize()
                                                        .padding(10.dp),
                                                    verticalArrangement = Arrangement.Center
                                                ) {
                                                    Text(
                                                        quiz.title.uppercase(Locale.getDefault()),
                                                        fontSize = 15.sp,
                                                        color = Color.White,
                                                        fontWeight = FontWeight.Bold,
                                                        modifier = Modifier.padding(start = 5.dp),
                                                        maxLines = 1,
                                                        overflow = TextOverflow.Ellipsis
                                                    )
                                                    Row {
                                                        Text(
                                                            text = LocalizedStringResource(R.string.created_at),
                                                            modifier = Modifier.padding(start = 5.dp, end = 5.dp),
                                                            fontSize = 15.sp,
                                                            color = Color.White,
                                                            fontWeight = FontWeight.Normal,
                                                            fontStyle = FontStyle.Italic
                                                        )

                                                        Text(
                                                            formatDate(quiz.createdAt),
                                                            fontSize = 15.sp,
                                                            color = Color.White,
                                                            fontWeight = FontWeight.Normal,
                                                            fontStyle = FontStyle.Italic
                                                        )
                                                    }
                                                    Row(Modifier.fillMaxWidth()) {
                                                        LazyRow {

                                                            item {
                                                                CustomSugestionChip(quiz.difficult)
                                                            }

                                                            item {
                                                                CustomSugestionChip(quiz.language.nativeName)
                                                            }

                                                            item {
                                                                CustomSugestionChip(quiz.resources)
                                                            }
                                                            item {
                                                                CustomSugestionChip(quiz.category.text)
                                                            }
                                                            item {
                                                                CustomSugestionChip(
                                                                    if (quiz.is_public)
                                                                        LocalizedStringResource(R.string.publictxt)
                                                                    else
                                                                        LocalizedStringResource(R.string.privatetxt)
                                                                )

                                                            }


                                                        }
                                                    }


                                                }
                                            }
                                        }

                                    }
                                    AnimatedVisibility(selectedQuiz == quiz.quiz_id) {
                                        Box(Modifier.height(50.dp).fillMaxWidth().clip(shape = RoundedCornerShape(bottomEnd = 10.dp, bottomStart = 10.dp)).background(SecondaryGrayTransparent)){
                                            Row(
                                                Modifier
                                                    .fillMaxWidth()
                                                    .padding(vertical = 5.dp),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Center
                                            ) {
                                                CustomButton(
                                                    onClick = {navController(AttemptsScreenESC(quiz.quiz_id, quiz.title))},
                                                    width = 0.2f,
                                                    containerColor = BlueCake
                                                ) {
                                                    Row(
                                                        Modifier.fillMaxSize(),
                                                        verticalAlignment = Alignment.CenterVertically,
                                                        horizontalArrangement = Arrangement.Center
                                                    ) {
                                                        Icon(
                                                            Icons.Default.RemoveRedEye,
                                                            tint = Color.White,
                                                            contentDescription = "iconPlay"
                                                        )
                                                    }
                                                }

                                                Spacer(Modifier.width(10.dp))



                                                CustomButton(
                                                    onClick = {navController(ModesQuizScreen(quiz.quiz_id))},
                                                    width = 0.3f
                                                ) {
                                                    Row(
                                                        Modifier.fillMaxSize(),
                                                        verticalAlignment = Alignment.CenterVertically,
                                                        horizontalArrangement = Arrangement.Center
                                                    ) {
                                                        Icon(
                                                            Icons.Default.PlayCircle,
                                                            tint = Color.White,
                                                            contentDescription = "iconPlay"
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }

                                    if(index != 0 && index % 5 == 0){
                                        Box(Modifier.height(80.dp), contentAlignment = Alignment.Center){
                                            BannerComponent(IdsAdmob.ID_BANNERS_QUIZZES_LIST)
                                        }
                                    }

                                }
                            }

                            item {
                                Box(
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(top = 10.dp, bottom = 10.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (myQuizzes?.data?.quizzes?.isEmpty() == true && page > 1) {
                                        CustomOutlineButton(
                                            onClick = {
                                                if (page > 1) {
                                                    page -= 1
                                                }
                                            },
                                            contentColor = MainOrange
                                        ) {
                                            Text(LocalizedStringResource(R.string.back))
                                        }
                                    } else if (myQuizzes?.data?.quizzes?.isEmpty() == false) {
                                        if (myQuizzes?.data?.quizzes?.size!! > 19) {
                                            CustomOutlineButton(
                                                onClick = { page += 1 },
                                                contentColor = MainOrange
                                            ) {
                                                Text(LocalizedStringResource(R.string.load_more))
                                            }
                                        }
                                    } else {
                                        Column(
                                            verticalArrangement = Arrangement.Center,
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Image(
                                                notFoundImage,
                                                contentDescription = "ImgNotFound",
                                                contentScale = ContentScale.Fit,
                                                modifier = Modifier
                                            )
                                            Text(
                                                LocalizedStringResource(R.string.no_quizzes_yet),
                                                modifier = Modifier.padding(end = 5.dp),
                                                fontSize = 25.sp,
                                                color = Color.White,
                                                fontWeight = FontWeight.Bold,
                                                textAlign = TextAlign.Center
                                            )
                                        }
                                    }


                                }
                            }

                        }


                    }

                }


            }


        }
    }

}