package com.yoinerdev.quizzesia.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yoinerdev.quizzesia.presentation.ui.theme.BlueCake
import com.yoinerdev.quizzesia.presentation.ui.theme.MainOrange

@Composable
fun SettingItem(
    onclick:()->Unit,
    text: String,
    icon:ImageVector
){
    Row (Modifier.fillMaxWidth().clickable { onclick() }) {
        Box(Modifier.weight(0.9f)){
            Row {
                Icon(icon, contentDescription = "iconTranslate", tint = BlueCake)
                Text(text, fontSize = 18.sp, color = Color.White, modifier = Modifier.padding(start = 10.dp))
            }
        }
        Box(Modifier.weight(0.1f), contentAlignment = Alignment.CenterEnd){
            Icon(Icons.AutoMirrored.Filled.ArrowForwardIos, contentDescription = "iconArrow", tint = MainOrange, modifier = Modifier.clickable { onclick() })
        }

    }
}