package com.yoinerdev.quizzesia.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.PeopleAlt
import androidx.compose.material.icons.filled.Score
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import com.composables.icons.lucide.Flame
import com.composables.icons.lucide.Lucide
import com.yoinerdev.quizzesia.data.dto.Data
import com.yoinerdev.quizzesia.presentation.ui.theme.BlackBackGround
import com.yoinerdev.quizzesia.presentation.ui.theme.BlueCake
import com.yoinerdev.quizzesia.presentation.ui.theme.MainOrange
import com.yoinerdev.quizzesia.presentation.ui.theme.SecondaryGray
import com.yoinerdev.quizzesia.presentation.ui.theme.SkyBlueCake
import com.yoinerdev.quizzesia.presentation.ui.theme.YellowCake

@Composable
fun TopBarNoTorch(
    logo:Painter,
    points:String = "",
    clipSize:Float = 30f,
    fontSize:Int = 25,
    subtitle:String = "",
    playersNumber:String
){
    Row(
        Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clip(RoundedCornerShape(bottomStart = clipSize, bottomEnd = clipSize))
            .background(SecondaryGray).padding(horizontal = 10.dp)
    ) {
        Box(
            Modifier
                .weight(0.2f)
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(top = 10.dp, bottom = 10.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                RotatingLoadingImage(logo, true)
            }
        }
        Box(
            Modifier
                .weight(0.6f)
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp, end = 10.dp),
            contentAlignment = Alignment.Center
        ) {

                Box(Modifier.fillMaxWidth(0.6f), contentAlignment = Alignment.Center){
                    Row (Modifier.fillMaxSize().clip(ShapeDefaults.Medium).background(Color.Transparent), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                        Icon(Icons.Default.EmojiEvents , "iconPeople", tint = MainOrange, modifier = Modifier.padding(horizontal = 5.dp).size(30.dp))
                        Text(points, fontSize = 25.sp, fontWeight = FontWeight.SemiBold, color = Color.White)

                    }
                }

        }

        Box(
            Modifier
                .weight(0.2f)
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(top = 10.dp, bottom = 10.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row (Modifier.fillMaxSize().clip(ShapeDefaults.Medium).background(BlueCake), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                Text(playersNumber, fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                Icon(Icons.Default.PeopleAlt , "iconPeople", tint = Color.White, modifier = Modifier.padding(horizontal = 5.dp))
            }

        }

    }
}