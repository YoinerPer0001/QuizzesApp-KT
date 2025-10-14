package com.yoinerdev.quizzesia.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yoinerdev.quizzesia.presentation.ui.theme.BlueCake
import com.yoinerdev.quizzesia.presentation.ui.theme.LilleCake

@Composable
fun CustomSliderSelector (
    numberQuestions:String,
    text:String,
    containerColor: Color = BlueCake,
    minRange:Float = 5f,
    maxRange:Float = 15f,
    onValueChange:(Int)-> Unit,
){
    ElevatedCard(
        onClick = {},
        colors = CardDefaults.cardColors(
            containerColor = containerColor
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
                    text,
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
                Row(Modifier.fillMaxSize()) {
                    Box(
                        Modifier
                            .fillMaxHeight()
                            .weight(0.2f),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Box(
                            Modifier
                                .clip(CircleShape)
                                .background(Color.Gray)
                                .size(50.dp), contentAlignment = Alignment.Center
                        ) {
                            Text(
                                numberQuestions,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                    Box(
                        Modifier
                            .fillMaxHeight()
                            .weight(0.8f),
                        contentAlignment = Alignment.Center
                    ) {
                        Slider(
                            value = numberQuestions.toFloat(),
                            onValueChange = { onValueChange(it.toInt()) },
                            valueRange = minRange..maxRange,
                            steps = 0,
                            modifier = Modifier.fillMaxWidth(),
                            colors = SliderDefaults.colors(
                                thumbColor = Color.White,
                                inactiveTickColor = Color.White,
                                activeTrackColor = LilleCake
                            )
                        )
                    }
                }
            }

        }
    }
}