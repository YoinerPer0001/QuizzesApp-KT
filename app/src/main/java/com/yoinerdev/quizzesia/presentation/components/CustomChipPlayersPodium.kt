package com.yoinerdev.quizzesia.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yoinerdev.quizzesia.data.dto.Players
import com.yoinerdev.quizzesia.presentation.ui.theme.BlackBackGround
import com.yoinerdev.quizzesia.presentation.ui.theme.MainOrange
import com.yoinerdev.quizzesia.presentation.ui.theme.SecondaryGrayTransparent

@Composable
fun CustomChipPlayersPodium(
    player:Players,
    position:String?,
    invertColor:Boolean = false
){
    SuggestionChip(
        label = { Text(player.name.replaceFirstChar { it.uppercaseChar() }, fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = Color.White, overflow = TextOverflow.Ellipsis, maxLines = 1) },
        onClick = {},
        modifier = Modifier.padding(horizontal = 5.dp, vertical = 5.dp).height(80.dp).fillMaxWidth(),
        colors = SuggestionChipDefaults.suggestionChipColors(
            containerColor = if (!invertColor) SecondaryGrayTransparent else BlackBackGround
        ),
        icon = {
            Box(Modifier.size(50.dp).clip(CircleShape).background(if (!invertColor) BlackBackGround else SecondaryGrayTransparent), contentAlignment = Alignment.Center){
                Text(position ?: "1", fontSize = 25.sp, color = Color.White)
            }
        }
    )
}