package com.yoinerdev.quizzesia.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yoinerdev.quizzesia.presentation.ui.theme.MainOrange

@Composable
fun CardEstatistics (
    title: String,
    value1: String,
    value2:String,
    value3:String,
    sub1:String,
    sub2:String,
    sub3:String
) {
    ElevatedCard(
        onClick = {},
        modifier = Modifier.fillMaxWidth().height(150.dp),
        shape = ShapeDefaults.Medium,
        colors = CardDefaults.cardColors(
            containerColor = MainOrange
        )
    ) {

        Column (Modifier.fillMaxSize().padding(5.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
            Box(modifier = Modifier.weight(0.2f).fillMaxWidth(), contentAlignment = Alignment.Center){
                Text(title, fontSize = 20.sp)
            }
            Box(modifier = Modifier.weight(0.8f).fillMaxWidth(), contentAlignment = Alignment.Center){
                Row (Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.weight(0.5f).padding( vertical = 10.dp)){
                        Column (Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
                            Text(value1, fontSize = 30.sp, fontWeight = FontWeight.SemiBold )
                            Text(sub1, fontSize = 15.sp, fontWeight = FontWeight.Normal )
                        }
                        VerticalDivider(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(2.dp)
                                .align(Alignment.CenterEnd),
                            color = Color.White
                        )
                    }
                    Box(modifier = Modifier.weight(0.5f).padding( vertical = 10.dp)){
                        Column (Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
                            Text(value2, fontSize = 30.sp, fontWeight = FontWeight.SemiBold )
                            Text(sub2, fontSize = 15.sp, fontWeight = FontWeight.Normal )
                        }
                        VerticalDivider(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(2.dp)
                                .align(Alignment.CenterEnd),
                            color = Color.White
                        )
                    }

                    Box(modifier = Modifier.weight(0.5f).padding( vertical = 10.dp)){
                        Column (Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
                            Text(value3, fontSize = 30.sp, fontWeight = FontWeight.SemiBold )
                            Text(sub3, fontSize = 15.sp, fontWeight = FontWeight.Normal )
                        }
                    }

                }
            }
        }

    }
}