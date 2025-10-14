package com.yoinerdev.quizzesia.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import com.yoinerdev.quizzesia.presentation.ui.theme.DarkSkyBlue
import com.yoinerdev.quizzesia.presentation.ui.theme.DarkSkyBlueCake
import com.yoinerdev.quizzesia.presentation.ui.theme.SecondaryGray

@Composable
fun CustomSugestionChip (
    text:String,
    containerColor: Color = SecondaryGray,
    borderColor:BorderStroke? = BorderStroke(1.dp, Color.White)
) {

    SuggestionChip(
        modifier = Modifier.padding(horizontal = 5.dp),
        colors = SuggestionChipDefaults.suggestionChipColors(
            containerColor = containerColor,
        ),
        border = borderColor,
        onClick = { },
        label = { Text(text, color = Color.White, maxLines = 1, overflow = TextOverflow.Ellipsis) }
    )
}