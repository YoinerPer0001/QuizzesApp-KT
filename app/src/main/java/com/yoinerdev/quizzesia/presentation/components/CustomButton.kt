package com.yoinerdev.quizzesia.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
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
fun CustomButton(
    onClick:()->Unit,
    containerColor: Color = MainOrange,
    contentColor:Color = Color.White,
    colorShadow :Color = MainOrange,
    height: Dp = 60.dp,
    width: Float = 1f,
    text: @Composable ()-> Unit
){
    Button(
        onClick = {onClick()},
        modifier = Modifier
            .fillMaxWidth(width)
            .height(height)
            .shadow(
                elevation = 10.dp,
                shape = ShapeDefaults.Small,
                spotColor = colorShadow,
                ambientColor = colorShadow
            ),
        shape = ShapeDefaults.Small,
        colors = ButtonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContentColor = Color.White,
            disabledContainerColor = Color.Gray
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
    ) {
        text()
    }
}