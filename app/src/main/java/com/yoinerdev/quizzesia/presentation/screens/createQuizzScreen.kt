package com.yoinerdev.quizzesia.presentation.screens

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yoinerdev.quizzesia.R
import com.yoinerdev.quizzesia.core.alerts.QuizzesAlerts
import com.yoinerdev.quizzesia.core.helpers.getFileMetaData
import com.yoinerdev.quizzesia.core.navegation.MainScreen
import com.yoinerdev.quizzesia.presentation.components.CustomButton
import com.yoinerdev.quizzesia.presentation.components.CustomOutlineButton
import com.yoinerdev.quizzesia.presentation.components.CustomSelector
import com.yoinerdev.quizzesia.presentation.components.CustomSwitch
import com.yoinerdev.quizzesia.presentation.components.CustomTField
import com.yoinerdev.quizzesia.presentation.components.RotatingLoadingImage
import com.yoinerdev.quizzesia.presentation.components.TopBarMain
import com.yoinerdev.quizzesia.presentation.ui.theme.BlackBackGround
import com.yoinerdev.quizzesia.presentation.ui.theme.BlueCake
import com.yoinerdev.quizzesia.presentation.ui.theme.DarkSkyBlueCake
import com.yoinerdev.quizzesia.presentation.ui.theme.LilleCake
import com.yoinerdev.quizzesia.presentation.ui.theme.OrangeTransparent
import com.yoinerdev.quizzesia.presentation.ui.theme.SkyBlueCake
import com.yoinerdev.quizzesia.presentation.ui.theme.YellowCake
import com.yoinerdev.quizzesia.presentation.viewmodels.AuthViewModel
import com.yoinerdev.quizzesia.presentation.viewmodels.QuizzesVM
import androidx.core.net.toUri
import com.yoinerdev.linternapro.presenter.components.IntersticialComponent
import com.yoinerdev.linternapro.presenter.components.IntersticialComponentRecompensados
import com.yoinerdev.quizzesia.IdsAdmob
import com.yoinerdev.quizzesia.core.helpers.getSavedLanguage
import com.yoinerdev.quizzesia.core.navegation.ModesQuizScreen
import com.yoinerdev.quizzesia.core.utils.LocalizedStringResource
import com.yoinerdev.quizzesia.core.utils.extractTextFromPdf
import com.yoinerdev.quizzesia.core.utils.limitText
import com.yoinerdev.quizzesia.core.utils.uriToFile
import com.yoinerdev.quizzesia.presentation.components.CustomSliderSelector
import com.yoinerdev.quizzesia.presentation.ui.theme.MainOrange
import com.yoinerdev.quizzesia.presentation.ui.theme.SecondaryGray
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

@Composable
fun CreateQuizScreen(
    authViewModel: AuthViewModel,
    navController: (Any) -> Unit,
    type: Int,
    uri: String?,
    quizzesVM: QuizzesVM,
) {
    val logo = painterResource(R.drawable.logo)
    val attemptsRemaining by authViewModel.attempsRemaining.collectAsState()
    val quiz_id by quizzesVM.quiz_id.collectAsState()
    val scrollState = rememberScrollState()
    var numberQuestionsValue by remember { mutableIntStateOf(10) }
    var timeQuestion by remember { mutableIntStateOf(2) }
    val difficults = listOf(
        "\uD83D\uDE0C" to "easy",
        "\uD83D\uDE05" to "medium",
        "\uD83E\uDD2F" to "hard"
    )
    var selectedDifficult by remember { mutableStateOf("medium") }
    var topicWrited by remember { mutableStateOf("Aleatorio") }
    var idLangSelected by remember { mutableStateOf("") }
    var languageSelected by remember { mutableStateOf("") }
    var idCatSelected by remember { mutableStateOf("") }

    val categories by quizzesVM.categoriesList.collectAsState()
    val languages by quizzesVM.languagesList.collectAsState()
    val state by quizzesVM.state.collectAsState()
    val context = LocalContext.current
    var nameFile by remember { mutableStateOf("Unknown") }
    var sizeFile by remember { mutableStateOf(0f) }

    var typeVF by remember { mutableStateOf(false) }
    var typeMOPT by remember { mutableStateOf(false) }
    var isPublic by remember { mutableStateOf(false) }


    var textPdfCrop by remember { mutableStateOf<String?>(null) }

    val typeQuestions by remember(typeVF, typeMOPT) {
        mutableStateOf(
            when {
                typeVF && typeMOPT -> "both"
                typeVF -> "t/f"
                typeMOPT -> "optm"
                else -> "none"
            }
        )
    }

    val UriParsed = uri?.toUri()
    var ShowIntersticial by remember { mutableStateOf(false) }
    val activity = context as Activity

    LaunchedEffect(uri) {
        withContext(Dispatchers.IO) {
            val pdfFile = UriParsed?.takeIf { it.scheme == "content" }?.let { uriToFile(context, it) }
            val textPdf =  pdfFile?.let { extractTextFromPdf(it) }
            val croppedText = if(type == 0) textPdf?.let { limitText(it, 500) } else limitText(topicWrited, 500)
            val (name, size) = UriParsed?.let { context.getFileMetaData(it) } ?: ("Unknown" to -1L)

            withContext(Dispatchers.Main) {
                textPdfCrop = croppedText
                nameFile = name
                sizeFile = (size.toDouble() / (1024*1024)).toFloat()
            }
        }
    }

    if (ShowIntersticial) {
        IntersticialComponent (IdsAdmob.ID_INSTERSTICIAL_FINISH_GAME) {
            if (it != null) {
                ShowIntersticial = false
                it.show(activity)
                navController(ModesQuizScreen(quiz_id))
            } else {
                ShowIntersticial = false
                navController(ModesQuizScreen(quiz_id))
            }
        }
    }




    LaunchedEffect(Unit) {
        getSavedLanguage(context).collect { lang ->
            quizzesVM.onCharge(lang)
        }

    }

    var isLoading by remember { mutableStateOf(false) }
    var ShowIntersticialPlus by remember { mutableStateOf(false) }

    when (state) {
        QuizzesAlerts.IsLoading -> {
            isLoading = true
        }

        QuizzesAlerts.SuccessDataCharged -> {
            isLoading = false
        }

        QuizzesAlerts.ErrortoCreate -> {
            Toast.makeText(context, LocalizedStringResource(R.string.error_create_quiz), Toast.LENGTH_SHORT).show()
            isLoading = false
        }

        QuizzesAlerts.SuccessCreated -> {
            ShowIntersticial = true
            authViewModel.getuserAttempts()
        }

        else -> {}
    }
    var showDialogAddAttempt by remember { mutableStateOf(false) }

    var showDialogNoAdds by remember { mutableStateOf(false) }

    if(ShowIntersticialPlus){
        IntersticialComponentRecompensados(IdsAdmob.ID_INSTERSTICIAL_ADD_CREDITS) { ad ->
            if (ad != null) {
                ad.show(activity) {
                    ShowIntersticialPlus = false
                    authViewModel.updateUserAttempts()
                }
            }else{
                if(ShowIntersticialPlus){
                    showDialogNoAdds = true
                    ShowIntersticialPlus = false
                }
            }
        }
    }


    Scaffold(
        Modifier.systemBarsPadding(),

        topBar = {
            TopBarMain(logo, attemptsRemaining, text = "Configurar Quizz"){
                showDialogAddAttempt = true
            }
        }

    ) { paddingValues ->

        AnimatedVisibility(showDialogAddAttempt) {
            AlertDialog(
                onDismissRequest = { showDialogAddAttempt = false },
                title = { Text(text = "🎬 ${LocalizedStringResource(R.string.earn_credits_ad)}", fontSize = 19.sp) },
                text = {
                    Column {
                        Text(LocalizedStringResource(R.string.watch_ad_credits))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(LocalizedStringResource(R.string.wait_credits_renew))
                    }
                },
                confirmButton = {
                    TextButton(onClick = {
                        // Acción para reproducir el anuncio
                        ShowIntersticialPlus = true
                        showDialogAddAttempt = false
                    }) {
                        Text(LocalizedStringResource(R.string.watch_ad))
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialogAddAttempt = false }) {
                        Text(LocalizedStringResource(R.string.cancel))
                    }
                }
            )
        }

        AnimatedVisibility(showDialogNoAdds) {
            AlertDialog(
                onDismissRequest = { showDialogNoAdds = false },
                title = { Text(text = LocalizedStringResource(R.string.no_ads_title), fontSize = 19.sp) },
                text = {
                    Column {
                        Text(LocalizedStringResource(R.string.no_ads_text_1))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(LocalizedStringResource(R.string.no_ads_text_2))
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showDialogNoAdds = false }) {
                        Text(LocalizedStringResource(R.string.understood))
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialogAddAttempt = true }) {
                        Text(LocalizedStringResource(R.string.retry))
                    }
                }
            )

        }

        if (isLoading) {
            Box(Modifier
                .fillMaxSize()
                .background(BlackBackGround)) {
                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    RotatingLoadingImage(stop = false)
                    Text(
                        text = LocalizedStringResource(R.string.loading),
                        fontSize = 18.sp,
                        fontStyle = FontStyle.Italic,
                        color = Color.White,
                        modifier = Modifier.padding(vertical = 20.dp)
                    )
                }
            }
        } else {


            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .background(BlackBackGround)
                    .padding(paddingValues)
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                //pdf charged type 0
                AnimatedVisibility(type == 0) {
                    Box(
                        Modifier
                            .wrapContentHeight()
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        ElevatedCard(
                            onClick = {},
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(70.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = OrangeTransparent
                            )
                        ) {
                            Row(Modifier.fillMaxWidth()) {
                                Box(
                                    Modifier
                                        .weight(0.2f)
                                        .fillMaxHeight()
                                        .padding(5.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        Icons.Default.PictureAsPdf,
                                        contentDescription = "pdfIcon",
                                        modifier = Modifier.size(40.dp)
                                    )
                                }
                                Box(Modifier.weight(0.8f)) {
                                    Column(
                                        Modifier
                                            .fillMaxSize()
                                            .padding(10.dp),
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            nameFile,
                                            fontSize = 18.sp,
                                            maxLines = 1,
                                            fontWeight = FontWeight.SemiBold,
                                            color = Color.White
                                        )
                                        Text(
                                            text = LocalizedStringResource(R.string.file_size).format(sizeFile),
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.Normal,
                                            color = Color.LightGray,
                                            fontStyle = FontStyle.Italic
                                        )
                                    }
                                }
                            }

                        }
                    }
                }

                AnimatedVisibility(type == 1) {
                    Box(
                        Modifier
                            .wrapContentHeight()
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CustomTField(
                            value = topicWrited,
                            onValueChange = { topicWrited = it },
                            label = LocalizedStringResource(R.string.topic),
                            leadingIcon = Icons.Default.TextFields,
                            maxLines = 2
                        )
                    }
                }

                //questions number
                Box(
                    Modifier
                        .wrapContentHeight()
                        .padding(vertical = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CustomSliderSelector(
                        numberQuestionsValue.toString(),
                        text = LocalizedStringResource(R.string.number_of_questions)
                    ) {
                        numberQuestionsValue = it
                    }

                }
                Box(
                    Modifier
                        .wrapContentHeight()
                        .padding(vertical = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CustomSliderSelector(
                        timeQuestion.toString(),
                        text = LocalizedStringResource(R.string.time_per_question),
                        maxRange = 5f,
                        minRange = 1f,
                        containerColor = SecondaryGray
                    ) {
                        timeQuestion = it
                    }

                }

                // difficult level
                Box(
                    Modifier
                        .wrapContentHeight()
                        .padding(vertical = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    ElevatedCard(
                        onClick = {},
                        modifier = Modifier,
                        colors = CardDefaults.cardColors(
                            containerColor = SkyBlueCake
                        )
                    ) {
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                                .height(90.dp)
                        ) {

                            Box(
                                Modifier
                                    .fillMaxWidth()
                                    .weight(0.3f)
                            ) {
                                Text(
                                    text = LocalizedStringResource(R.string.difficulty_level),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.White
                                )

                            }
                            Box(
                                Modifier
                                    .fillMaxWidth()
                                    .weight(0.7f)
                            ) {
                                Row(
                                    Modifier.fillMaxSize(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceAround
                                ) {
                                    difficults.forEach { (emoji, label) ->
                                        OutlinedButton(
                                            onClick = { selectedDifficult = label },
                                            colors = ButtonDefaults.outlinedButtonColors(
                                                containerColor = if (selectedDifficult == label) DarkSkyBlueCake else Color.Transparent,),
                                            border = BorderStroke(
                                                1.dp,
                                                color = if (selectedDifficult == label) Color.White else Color.LightGray
                                            ),
                                            shape = ShapeDefaults.Medium,
                                            contentPadding = PaddingValues(
                                                vertical = 2.dp,
                                                horizontal = 10.dp
                                            ),
                                            modifier = Modifier.padding(4.dp)
                                        ) {
                                            Text(
                                                text = "$emoji $label",
                                                fontSize = 15.sp,
                                                color = Color.White
                                            )
                                        }
                                    }
                                }
                            }

                        }
                    }
                }
                //questions preferences
                Box(
                    Modifier
                        .wrapContentHeight()
                        .padding(vertical = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    ElevatedCard(
                        onClick = {},
                        colors = CardDefaults.cardColors(
                            containerColor = YellowCake
                        )
                    ) {
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                                .height(180.dp)
                        ) {
                            Box(
                                Modifier
                                    .fillMaxWidth()
                                    .weight(0.2f)
                            ) {
                                Text(
                                    text = LocalizedStringResource(R.string.question_preferences),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.White
                                )
                            }

                            Box(
                                Modifier
                                    .fillMaxWidth()
                                    .weight(0.7f)
                            ) {
                                Row(
                                    Modifier.fillMaxSize(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceAround
                                ) {

                                    Box(
                                        Modifier
                                            .weight(0.5f)
                                            .fillMaxHeight(), contentAlignment = Alignment.TopCenter
                                    ) {
                                        Column(
                                            Modifier.fillMaxSize(),
                                            verticalArrangement = Arrangement.Top
                                        ) {
                                            CustomSelector(
                                                label = LocalizedStringResource(R.string.language),
                                                languages,
                                                onSelectedLan = { id, lang ->
                                                    idLangSelected = id
                                                    languageSelected = lang
                                                })
                                            CustomSelector(
                                                label = LocalizedStringResource(R.string.category),
                                                categories,
                                                onSelectedLan = { id, cat ->
                                                    idCatSelected = id
                                                })
                                        }

                                    }
                                    Box(Modifier.weight(0.5f)) {
                                        Column(
                                            Modifier
                                                .fillMaxSize()
                                                .padding(horizontal = 10.dp)
                                        ) {
                                            Row(
                                                Modifier.fillMaxWidth(),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                CustomSwitch(LocalizedStringResource(R.string.true_false)) {
                                                    typeVF = it
                                                }
                                            }
                                            Row(
                                                Modifier.fillMaxWidth(),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                CustomSwitch(LocalizedStringResource(R.string.multiple_choice)) {
                                                    typeMOPT = it
                                                }
                                            }
                                            Row(
                                                Modifier.fillMaxWidth(),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                CustomSwitch(LocalizedStringResource(R.string.publictxt)) {
                                                    isPublic = it
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                Box(
                    Modifier
                        .wrapContentHeight()
                        .padding(vertical = 10.dp),
                    contentAlignment = Alignment.Center
                ) {

                    Column(
                        Modifier
                            .fillMaxWidth()
                            .height(180.dp), horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Box(Modifier.padding(vertical = 20.dp)) {
                            val msgErr1 = LocalizedStringResource(R.string.error_language_required)
                            val msgErr2 = LocalizedStringResource(R.string.error_category_required)
                            val msgErr3 = LocalizedStringResource(R.string.error_type_question_required)
                            CustomButton(
                                onClick = {
                                    if(idLangSelected.isEmpty()){
                                        Toast.makeText(
                                            context,
                                            msgErr1,
                                            Toast.LENGTH_LONG
                                        ).show()
                                        return@CustomButton
                                    }

                                    if(idCatSelected.isEmpty()){
                                        Toast.makeText(
                                            context,
                                            msgErr2,
                                            Toast.LENGTH_LONG
                                        ).show()
                                        return@CustomButton
                                    }

                                    if(typeQuestions == "none"){
                                        Toast.makeText(
                                            context,
                                            msgErr3,
                                            Toast.LENGTH_LONG
                                        ).show()
                                        return@CustomButton
                                    }

                                    quizzesVM.createQuizHandWritten(
                                        title = if(type == 1) topicWrited else nameFile.split(".")[0],
                                        topic = if(type == 1) topicWrited else textPdfCrop.toString(),
                                        number = numberQuestionsValue,
                                        language = languageSelected,
                                        difficult = selectedDifficult,
                                        type = typeQuestions,
                                        is_public = isPublic,
                                        lan_id = idLangSelected,
                                        cat_id = idCatSelected,
                                        resources = if(type == 1) "handwritten" else "pdf",
                                        timeQuestion = timeQuestion
                                    )
                                },
                                width = 0.7f,
                                colorShadow = Color.Transparent
                            ) {
                                Text(
                                    LocalizedStringResource(R.string.generate_quiz),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }

                        CustomOutlineButton(
                            onClick = { navController(MainScreen) },
                            contentColor = BlueCake,
                            width = 0.2f
                        ) {
                            Icon(
                                Icons.Default.ArrowBackIosNew,
                                contentDescription = "backIcon",
                                modifier = Modifier.size(40.dp)
                            )
                        }
                    }

                }


            }

        }
    }
}