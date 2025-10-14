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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Quiz
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
import com.yoinerdev.quizzesia.presentation.ui.theme.GreenCake
import com.yoinerdev.quizzesia.presentation.ui.theme.GreenCakeTransparent
import com.yoinerdev.quizzesia.presentation.ui.theme.MainOrange
import com.yoinerdev.quizzesia.presentation.ui.theme.SecondaryGray
import com.yoinerdev.quizzesia.presentation.ui.theme.SecondaryGrayTransparent
import com.yoinerdev.quizzesia.presentation.ui.theme.SkyBlueCake
import com.yoinerdev.quizzesia.presentation.ui.theme.YellowCake
import com.yoinerdev.quizzesia.presentation.viewmodels.AuthViewModel
import com.yoinerdev.quizzesia.presentation.viewmodels.QuizzesVM
import java.util.Locale

@Composable
fun PublicQuizzesScreen(
    viewModel: AuthViewModel,
    quizzesVM: QuizzesVM,
    navController: (Any) -> Unit,
) {
    val logo = painterResource(R.drawable.logo)
    val user by viewModel.user.collectAsState()
    val attemptsRemaining by viewModel.attempsRemaining.collectAsState()
    var chipSelected by remember { mutableStateOf("") }
    val context = LocalContext.current
    val PublicQuizzes by quizzesVM.PublicQuizzes.collectAsState()

    val categories by quizzesVM.categoriesList.collectAsState()

    var isLoading by remember { mutableStateOf(false) }

    var selectedQuiz by remember { mutableStateOf("") }

    val state by quizzesVM.state.collectAsState()
    val notFoundImage = painterResource(R.drawable.not_found)

    var page by remember { mutableIntStateOf(1) }

    when (state) {

        QuizzesAlerts.IsLoading -> {
            isLoading = true
        }

        QuizzesAlerts.ErrorToGetQuizzesPublic -> {
            quizzesVM.getUserQuizzes(page, null)
            Toast.makeText(context, "Error al obtener quizzes, reintentando...", Toast.LENGTH_SHORT)
                .show()
            isLoading = true
        }

        QuizzesAlerts.SuccessToGetQuizzesPublic -> {
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
        quizzesVM.getPublicQuizzes(page, chipSelected)
    }

    LaunchedEffect(Unit) {
        quizzesVM.getPublicQuizzes(page, null)
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

            BottomBarNav(user = user, btnSelected = 2) {
                navController(it)
            }

        },

        topBar = {
            TopBarMain(
                logo,
                attemptsRemaining,
                text = LocalizedStringResource(R.string.public_quizzes),
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
                            LocalizedStringResource(R.string.loading_quizzes),
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
//                            PublicQuizzes?.data?.quizzes ?: emptyList()
                            itemsIndexed(PublicQuizzes?.data?.quizzes ?: emptyList()) { index, quiz ->
                                Column(Modifier.padding(bottom = 10.dp)) {
                                    ElevatedCard(
                                        onClick = {
                                            if(selectedQuiz != quiz.quiz_id){
                                                selectedQuiz = quiz.quiz_id
                                            }else{
                                                selectedQuiz = ""
                                            }
                                                  },
                                        modifier = Modifier
                                            .height(130.dp)
                                            .fillMaxWidth()
                                            .padding(),
                                        colors = CardDefaults.cardColors(
                                            containerColor = SecondaryGray
                                        ),
                                        shape = RoundedCornerShape(
                                            topStart = 10.dp,
                                            topEnd = 10.dp,
                                            bottomEnd = if (selectedQuiz != quiz.quiz_id)  10.dp else 0.dp,
                                            bottomStart = if (selectedQuiz != quiz.quiz_id)  10.dp else 0.dp),
                                            ) {

                                        Row(
                                            Modifier
                                                .fillMaxWidth()
                                                .padding(5.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.Center
                                        ) {
                                            Box(Modifier.weight(0.2f)) {
                                                Column(
                                                    Modifier.fillMaxSize(),
                                                    horizontalAlignment = Alignment.CenterHorizontally,
                                                    verticalArrangement = Arrangement.Center
                                                ) {
                                                    Box(
                                                        Modifier
                                                            .size(65.dp)
                                                            .clip(CircleShape)
                                                            .border(1.dp, shape = CircleShape, color = Color.White)
                                                            .background(SecondaryGray),
                                                        contentAlignment = Alignment.Center
                                                    ) {
                                                        Text(
                                                            quiz.questionCount.toString(),
                                                            color = Color.White,
                                                            fontSize = 24.sp,
                                                            fontWeight = FontWeight.Bold
                                                        )
                                                    }
                                                    Text(
                                                        text = LocalizedStringResource(R.string.questions),
                                                        color = Color.White,
                                                        fontSize = 14.sp
                                                    )

                                                }
                                            }
                                            Box(Modifier.weight(0.8f)) {
                                                Column(
                                                    Modifier
                                                        .fillMaxSize()
                                                        .padding(start = 10.dp),
                                                    horizontalAlignment = Alignment.Start,
                                                    verticalArrangement = Arrangement.Center
                                                ) {
                                                    Text(
                                                        quiz.title.uppercase(Locale.getDefault()),
                                                        color = Color.White,
                                                        fontSize = 18.sp,
                                                        fontWeight = FontWeight.Bold,
                                                        maxLines = 1,
                                                        overflow = TextOverflow.Ellipsis,
                                                        modifier = Modifier.padding(start = 5.dp)
                                                    )
                                                    HorizontalDivider(Modifier.height(1.dp).padding(start = 5.dp), color = Color.White)

                                                    Row {
                                                        Text(
                                                            text = LocalizedStringResource(R.string.creator),
                                                            color = Color.White,
                                                            fontSize = 15.sp,
                                                            fontWeight = FontWeight.Normal,
                                                            modifier = Modifier.padding(horizontal = 5.dp)
                                                        )
                                                        Text(
                                                            quiz.creator?.name.toString(),
                                                            color = Color.White,
                                                            fontSize = 15.sp,
                                                            fontWeight = FontWeight.SemiBold,
                                                        )
                                                    }

                                                    Row {
                                                        Text(
                                                            text = LocalizedStringResource(R.string.created_at),
                                                            color = Color.White,
                                                            fontSize = 15.sp,
                                                            fontWeight = FontWeight.Normal,
                                                            modifier = Modifier.padding(horizontal = 5.dp),
                                                            fontStyle = FontStyle.Italic
                                                        )
                                                        Text(
                                                            formatDate(quiz.createdAt),
                                                            color = Color.White,
                                                            fontSize = 15.sp,
                                                            fontWeight = FontWeight.Normal,
                                                            fontStyle = FontStyle.Italic
                                                        )
                                                    }

                                                    LazyRow {
                                                        item {
                                                            CustomSugestionChip(quiz.difficult, containerColor = Color.Transparent, borderColor = BorderStroke(1.dp, color = Color.White))
                                                        }
                                                        item {
                                                            CustomSugestionChip(quiz.language.nativeName, containerColor = Color.Transparent, borderColor = BorderStroke(1.dp, color = Color.White))
                                                        }
                                                        item {
                                                            CustomSugestionChip(quiz.category.text, containerColor = Color.Transparent, borderColor = BorderStroke(1.dp, color = Color.White))
                                                        }
                                                    }


                                                }
                                            }
                                        }

                                    }
                                    AnimatedVisibility(selectedQuiz == quiz.quiz_id) {
                                        Box(
                                            Modifier
                                                .fillMaxWidth()
                                                .clip(
                                                    shape = RoundedCornerShape(
                                                        bottomEnd = 10.dp,
                                                        bottomStart = 10.dp
                                                    )
                                                )
                                                .height(50.dp)
                                                .background(SecondaryGrayTransparent)
                                        ) {
                                            Row(
                                                Modifier
                                                    .fillMaxWidth()
                                                    .padding(vertical = 5.dp),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Center
                                            ) {
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
                                                        Text(
                                                            text = LocalizedStringResource(R.string.play),
                                                            modifier = Modifier.padding(start = 5.dp)
                                                        )

                                                    }
                                                }
                                            }
                                        }
                                    }
                                    if(index != 0 && index % 10 == 0){
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
                                    if (PublicQuizzes?.data?.quizzes?.isEmpty() == true && page > 1) {
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
                                    } else if (PublicQuizzes?.data?.quizzes?.isEmpty() == false) {
                                        if (PublicQuizzes?.data?.quizzes?.size!! > 19) {
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