package com.yoinerdev.quizzesia.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.yoinerdev.quizzesia.presentation.ui.theme.MainOrange

@Composable
fun IconNav(
    text: String,
    selectedColor: Color = MainOrange,
    unselectedColor:Color = Color.LightGray,
    icon: ImageVector = Icons.Outlined.Home,
    selected: Boolean,
    isImage:Boolean = false,
    urlImage:String = "",
    onClick: () -> Unit
) {
    Column(
        Modifier

            
            .fillMaxWidth(0.5f)
            .fillMaxHeight()
            .clickable (interactionSource = remember { MutableInteractionSource() }, indication = null){
                onClick()
            }
            .padding(vertical = 5.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(!isImage){
            Icon(
                icon,
                tint = if(selected) selectedColor else unselectedColor,
                contentDescription = "HomeIcon",
                modifier = Modifier.size(30.dp)
            )
        }else{
            AsyncImage(
                urlImage,
                contentDescription = "imagePerfil",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
            )
        }

        Text(text, fontSize = 10.sp, color = if(selected) selectedColor else unselectedColor)
    }
}