package com.yoinerdev.quizzesia.presentation.screens

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieConstants
import com.yoinerdev.quizzesia.R
import com.yoinerdev.quizzesia.presentation.components.animations.LottieAnimationScore

@Composable
fun PodiumElement(
    percent: Float = 0f,
    color: Color,
    medall: String
) {
    var expanded by remember { mutableStateOf(false) }

    // Altura animada
    val height by animateFloatAsState(
        targetValue = if (expanded) percent else 0f,
        animationSpec = tween(durationMillis = 800, easing = LinearOutSlowInEasing)
    )

    LaunchedEffect(percent) {
        expanded = !expanded
    }

    Box(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(height)
            .background(color), contentAlignment = Alignment.TopCenter
    ) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(medall, fontSize = 40.sp)
            if(medall == "\uD83E\uDD47"){
                LottieAnimationScore(R.raw.winner)
            }

        }

    }
}