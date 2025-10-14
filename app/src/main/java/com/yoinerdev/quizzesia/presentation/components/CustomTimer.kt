package com.yoinerdev.quizzesia.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun CountdownTimer(
    totalMinutes: Int?,
    onChange:(percentRemaining:Float)-> Unit,
    resetKey: String,
    onFinish: () -> Unit = {}
) {
    if(totalMinutes != null){
        var remainingSeconds by remember (resetKey) { mutableIntStateOf(totalMinutes * 60) }
        val totalSecs = totalMinutes*60

        // Se dispara una sola vez al componer
        LaunchedEffect(resetKey) {
            while (remainingSeconds > 0) {
                delay(1000L)
                val percent = (1f- (remainingSeconds.toFloat()/totalSecs.toFloat()))
                onChange(percent)
                remainingSeconds--
            }
            onFinish()
        }

        // Calcula minutos y segundos actuales
        val minutes = remainingSeconds / 60
        val seconds = remainingSeconds % 60
        val formattedTime = String.format("%02d:%02d", minutes, seconds)

        Text(
            formattedTime,
            fontSize = 20.sp,
            color = Color.White,
            modifier = Modifier.padding(start = 5.dp)
        )
    }else{
        CircularProgressIndicator()
    }

}