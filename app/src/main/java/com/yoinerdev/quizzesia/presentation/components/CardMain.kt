package com.yoinerdev.quizzesia.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yoinerdev.quizzesia.presentation.ui.theme.BlueCake

@Composable
fun CardMain(
    icon:@Composable ()->Unit,
    cardSize:Dp = 150.dp,
    shape:Shape = ShapeDefaults.Medium,
    color:Color = BlueCake,
    text :String,
    onClick:()->Unit
){
    Box(Modifier.size(cardSize).clip(shape = shape).background(color).clickable { onClick() }){
        Column (Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
            icon()
            Text(text, fontSize = 22.sp, color = Color.White, modifier = Modifier.padding(top = 10.dp), textAlign = TextAlign.Center)
        }
    }
}