package com.yoinerdev.quizzesia.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlusOne
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yoinerdev.quizzesia.R
import com.yoinerdev.quizzesia.data.dto.Data
import com.yoinerdev.quizzesia.presentation.components.animations.LottieAnimationScore
import com.yoinerdev.quizzesia.presentation.ui.theme.BlackBackGround
import com.yoinerdev.quizzesia.presentation.ui.theme.MainOrange
import com.yoinerdev.quizzesia.presentation.ui.theme.SecondaryGray

@Composable
fun TopBarMain(
    logo:Painter? = null,
    attemptsRemaining: Data?,
    text:String = "",
    clipSize:Float = 30f,
    icon : ImageVector? = null,
    fontSize:Int = 25,
    subtitle:String = "",
    onClickedIon:()-> Unit
){
    Row(
        Modifier
            .fillMaxWidth()
            .height(80.dp)

            .clip(RoundedCornerShape(bottomStart = clipSize, bottomEnd = clipSize))
            .background(SecondaryGray)
    ) {

        Box(
            Modifier
                .weight(0.1f)
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
                if(logo != null){
                    RotatingLoadingImage(logo, true)
                }
                else if(icon != null){
                    Icon(icon, "iconCheck", tint = Color.Green, modifier = Modifier.size(70.dp))
                }

            }
        }
        Box(
            Modifier
                .weight(0.4f)
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp, end = 10.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Row (
                Modifier.fillMaxSize(),
            ) {
                Box(Modifier.weight(1f).fillMaxHeight(), contentAlignment = Alignment.Center){
                   Column (Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment =  if(subtitle.isNotEmpty()) Alignment.Start else Alignment.CenterHorizontally) {
                       Text(text, fontSize = fontSize.sp, color = Color.White)
                       if(subtitle.isNotEmpty()){
                           Text(subtitle, fontSize = (fontSize-5).sp, color = Color.LightGray, fontStyle = FontStyle.Italic)
                       }

                   }
                }
                Box(
                    Modifier
                        .size(80.dp, 50.dp)
                        .clip(ShapeDefaults.Medium)
                        .background(BlackBackGround)
                ) {
                    Row(
                        Modifier
                            .fillMaxSize().clickable { onClickedIon() },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {

                        if(attemptsRemaining != null){
                           Box(){
                               Icon(Icons.Default.PlusOne, contentDescription = "iconPlus", tint = Color.White, modifier = Modifier.offset(x = (-10).dp, y = 5.dp).size(20.dp))

                               LottieAnimationScore(R.raw.fire, size = 0.3f)
                           }

                            Text(attemptsRemaining.attempts_remaining.toString(), color = Color.White, fontSize = 25.sp, modifier = Modifier.padding(start = 5.dp))

                        }else{
                            CircularProgressIndicator(trackColor = MainOrange)
                        }

                    }
                }
            }
        }

    }
}