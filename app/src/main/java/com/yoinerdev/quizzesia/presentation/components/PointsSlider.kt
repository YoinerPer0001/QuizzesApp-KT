package com.yoinerdev.quizzesia.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.yoinerdev.quizzesia.presentation.ui.theme.Purple40

@Composable
fun PointsSlide(
    index:Int,
    numberPoints:Int
){

    Row (Modifier.fillMaxWidth().padding(vertical = 20.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center){
        for (i in 0..numberPoints){
            Box(Modifier.size(if(index == i) 15.dp else 10.dp).clip(CircleShape).background(if(index == i) Color.LightGray else Purple40 ).padding(horizontal = 5.dp))
            Spacer(Modifier.padding(horizontal = 2.dp))
        }
    }

}