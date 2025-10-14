package com.yoinerdev.quizzesia.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yoinerdev.quizzesia.presentation.ui.theme.MainOrange
import androidx.compose.ui.unit.LayoutDirection
import com.yoinerdev.quizzesia.presentation.ui.theme.OrangeTransparent

@Composable
fun CustomAnswerOption (
    selected:Boolean = false,
    text:String,
    letterOption:String,
    onClick:()-> Unit,
){
    Row (Modifier.fillMaxWidth().padding(vertical = 10.dp)) {
        ElevatedCard(
            onClick = {onClick()},
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = if(!selected) Color.White else OrangeTransparent
            ),
            shape = CircleShape
        ) {
            AnimatedContent(
                targetState = selected,
                transitionSpec = {
                    // Si pasa de false→true, desliza de derecha a izquierda
                    // Si pasa de true→false, desliza de izquierda a derecha
                    if (targetState) {
                        slideInHorizontally{ width -> width } + fadeIn() togetherWith
                                slideOutHorizontally{ width -> -width } + fadeOut()
                    } else {
                        slideInHorizontally{ width -> -width } + fadeIn() togetherWith
                                slideOutHorizontally{ width -> width } + fadeOut()
                    }
                },
                label = "RowSlideAnimation"
            ) { isSelected ->
                CompositionLocalProvider(
                    LocalLayoutDirection provides if (isSelected) LayoutDirection.Rtl else LayoutDirection.Ltr
                ) {
                    Row(
                        Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box(
                            Modifier.fillMaxHeight().weight(0.2f),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                Modifier
                                    .size(50.dp)
                                    .clip(CircleShape)
                                    .background(MainOrange),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(letterOption, fontSize = 25.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                        Box(
                            Modifier.fillMaxHeight().weight(0.6f),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Text(text, color = if(!selected) Color.Black else Color.White, fontSize = 25.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }

        }
    }
}