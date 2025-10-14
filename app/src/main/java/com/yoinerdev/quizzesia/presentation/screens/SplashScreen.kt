package com.yoinerdev.quizzesia.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yoinerdev.quizzesia.R
import com.yoinerdev.quizzesia.core.utils.LocalizedStringResource
import com.yoinerdev.quizzesia.presentation.components.BottomBarNav
import com.yoinerdev.quizzesia.presentation.components.RotatingLoadingImage
import com.yoinerdev.quizzesia.presentation.components.TopBarMain
import com.yoinerdev.quizzesia.presentation.ui.theme.BlackBackGround
import com.yoinerdev.quizzesia.presentation.ui.theme.MainOrange

@Composable
fun SplashScreen() {
    Scaffold(
        Modifier.systemBarsPadding(),

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

            RotatingLoadingImage()
            Text(
                LocalizedStringResource(R.string.loading),
                maxLines = 1,
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal,
                color = Color.White,
                modifier = Modifier.padding(vertical = 15.dp)

                )

        }
    }
}