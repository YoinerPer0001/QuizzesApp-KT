package com.yoinerdev.quizzesia.presentation.screens

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForwardIos
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.ArrowForwardIos
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yoinerdev.quizzesia.R
import com.yoinerdev.quizzesia.core.helpers.saveFirstOpen
import com.yoinerdev.quizzesia.core.navegation.LoginScreen
import com.yoinerdev.quizzesia.core.utils.LocalizedStringResource
import com.yoinerdev.quizzesia.domain.model.CarouselItem
import com.yoinerdev.quizzesia.presentation.components.CustomButton
import com.yoinerdev.quizzesia.presentation.components.CustomOutlineButton
import com.yoinerdev.quizzesia.presentation.components.PointsSlide
import com.yoinerdev.quizzesia.presentation.components.animations.LottieAnimationScore
import com.yoinerdev.quizzesia.presentation.ui.theme.BlackBackGround
import com.yoinerdev.quizzesia.presentation.ui.theme.BlueCake
import com.yoinerdev.quizzesia.presentation.ui.theme.LilleCake
import com.yoinerdev.quizzesia.presentation.ui.theme.MainOrange
import com.yoinerdev.quizzesia.presentation.ui.theme.Purple40
import com.yoinerdev.quizzesia.presentation.ui.theme.SecondaryGray

@SuppressLint("UnusedContentLambdaTargetStateParameter")
@Composable
fun StartScreen(
    navController: (Any) -> Unit
) {

    val context = LocalContext.current
    var index by remember { mutableIntStateOf(0) }

    BackHandler {
        if (index > 0) {
            index -= 1
        }
    }

    val title1 = LocalizedStringResource(R.string.start_title_1)
    val subtitle1 = LocalizedStringResource(R.string.start_subtitle_1)
    val desc1 = LocalizedStringResource(R.string.start_desc_1)

    val title2 = LocalizedStringResource(R.string.start_title_2)
    val desc2 = LocalizedStringResource(R.string.start_desc_2)

    val title3 = LocalizedStringResource(R.string.start_title_3)
    val desc3 = LocalizedStringResource(R.string.start_desc_3)

    val title4 = LocalizedStringResource(R.string.start_title_4)
    val desc4 = LocalizedStringResource(R.string.start_desc_4)

    val startButton = LocalizedStringResource(R.string.start_button)



    val items = remember {
        listOf(
            CarouselItem(
                0,
                title = title1,
                subtitle = subtitle1,
                imageResId = R.raw.learning,
                contentDescription = desc1
            ),
            CarouselItem(
                1,
                title = title2,
                subtitle = "",
                imageResId = R.raw.clock,
                contentDescription = desc2
            ),
            CarouselItem(
                2,
                title = title3,
                subtitle = "",
                imageResId = R.raw.community,
                contentDescription = desc3
            ),
            CarouselItem(
                3,
                title = title4,
                subtitle = "",
                imageResId = R.raw.coins,
                contentDescription = desc4
            ),
        )
    }

    var dragOffset by remember { mutableFloatStateOf(0f) }
    val minSwipeDistance = 120f // distancia mínima para considerar swipe

    val numberCards by remember { mutableIntStateOf(3) }

    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        bottomBar = {
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(Modifier.weight(0.5f), contentAlignment = Alignment.CenterStart) {
                    if (index > 0) {
                        Icon(
                            Icons.Outlined.ArrowBackIosNew,
                            "iosIcon",
                            tint = MainOrange,
                            modifier = Modifier
                                .size(40.dp)
                                .clickable {
                                    index -= 1
                                })
                    }

                }
                Box(Modifier.weight(0.5f), contentAlignment = Alignment.CenterEnd) {
                    if (index < numberCards)
                        Icon(
                            Icons.AutoMirrored.Outlined.ArrowForwardIos,
                            "iosIcon",
                            tint = MainOrange,
                            modifier = Modifier
                                .size(40.dp)
                                .clickable {
                                    index += 1
                                })
                }
            }
        }
    ) { paddingValues ->

        Column(
            Modifier
                .fillMaxSize()
                .background(BlackBackGround)
                .padding(paddingValues)
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onDragStart = {
                            dragOffset = 0f
                        },
                        onHorizontalDrag = { _, dragAmount ->
                            dragOffset += dragAmount
                        },
                        onDragEnd = {
                            when {
                                dragOffset > minSwipeDistance -> {
                                    if (index > 0) {
                                        index = (index - 1)
                                    }
                                }

                                dragOffset < -minSwipeDistance -> {
                                    if (index < numberCards) {
                                        index = (index + 1) // derecha a izquierda
                                    }

                                }

                                else -> {}
                            }
                            dragOffset = 0f
                        }
                    )

                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ElevatedCard(
                onClick = {},
                modifier = Modifier
                    .width(350.dp)
                    .height(500.dp).padding(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = SecondaryGray
                )
            ) {


                AnimatedContent(
                    targetState = index,
                ) {
                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(30.dp),
                        verticalArrangement = Arrangement.SpaceAround,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box() {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    items[index].title,
                                    maxLines = 2,
                                    textAlign = TextAlign.Center,
                                    fontSize = 25.sp,
                                    color = if (index == 0) Color.White else MainOrange,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    items[index].subtitle.toString(),
                                    maxLines = 2,
                                    textAlign = TextAlign.Center,
                                    fontSize = 40.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MainOrange,

                                    )
                            }
                        }


//                        Image(
//                            painterResource(items[index].imageResId),
//                            "imagCard",
//                            contentScale = ContentScale.Fit,
//                            modifier = Modifier.padding(vertical = 10.dp)
//                        )
                        Box(
                            modifier = Modifier.fillMaxWidth().height(250.dp)
                        ){
                            LottieAnimationScore(items[index].imageResId)
                        }




                        Text(
                            items[index].contentDescription,
                            textAlign = TextAlign.Center,
                            fontSize = 18.sp,
                            color = Color.White
                        )



                    }

                }

            }

            PointsSlide(index, numberCards)
            AnimatedVisibility(index == numberCards, enter = fadeIn(), exit = fadeOut()) {
                CustomButton(
                    onClick = {
                        saveFirstOpen(context, false)
                        navController(LoginScreen)
                    },
                    width = 0.5f
                ) {
                    Text(startButton, fontSize = 20.sp, color = Color.White)
                }
            }


        }

    }
}