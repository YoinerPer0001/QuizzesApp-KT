package com.yoinerdev.quizzesia.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yoinerdev.quizzesia.presentation.ui.theme.MainOrange

@Composable
fun CustomOutlineButton(
    onClick:()->Unit,
    containerColor: Color = MainOrange,
    contentColor:Color = Color.White,
    colorShadow :Color = MainOrange,
    height: Dp = 60.dp,
    width: Float = 0.5f,
    text: @Composable ()-> Unit
){
    OutlinedButton(
        onClick = {onClick()},
        border = BorderStroke(1.dp, color = contentColor),
        modifier = Modifier
            .fillMaxWidth(width)
            .height(height),
        shape = ShapeDefaults.Small,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = contentColor
        )
    ) {
        text()
    }
}