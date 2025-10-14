package com.yoinerdev.quizzesia.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yoinerdev.quizzesia.presentation.ui.theme.LilleCake

@Composable
fun CustomSwitch(
    text: String,
    fontSize:TextUnit = 18.sp,
    onCheckedChange: (Boolean) -> Unit,
) {
    var value by remember { mutableStateOf(false) }
    Switch(

        onCheckedChange = {
            value = it
            onCheckedChange(value)
        },
        checked = value,
        colors = SwitchDefaults.colors(
            checkedThumbColor = LilleCake
        )
    )
    Text(
        text,
        fontSize = fontSize,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(start = 5.dp),
        color = Color.White
    )
}