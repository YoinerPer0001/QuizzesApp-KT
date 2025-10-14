package com.yoinerdev.quizzesia.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.FactCheck
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.outlined.FactCheck
import androidx.compose.material.icons.outlined.GroupAdd
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.HistoryEdu
import androidx.compose.material.icons.outlined.HistoryToggleOff
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LibraryAddCheck
import androidx.compose.material.icons.outlined.Quiz
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.yoinerdev.quizzesia.R
import com.yoinerdev.quizzesia.core.navegation.MainScreen
import com.yoinerdev.quizzesia.core.navegation.ModesQuizScreen
import com.yoinerdev.quizzesia.core.navegation.MyQuizzesEsc
import com.yoinerdev.quizzesia.core.navegation.PerfilESC
import com.yoinerdev.quizzesia.core.navegation.PublicQuizzesEsc
import com.yoinerdev.quizzesia.core.utils.LocalizedStringResource
import com.yoinerdev.quizzesia.domain.model.User
import com.yoinerdev.quizzesia.presentation.ui.theme.SecondaryGray

@Composable
fun BottomBarNav(
    user: User?,
    btnSelected:Int,
    onSelected:(Any)->Unit
){

    Row(Modifier
        .fillMaxWidth()
        .height(70.dp)
        .clip(RoundedCornerShape(topStartPercent = 90, topEndPercent = 90))
        .background(SecondaryGray)) {

        Box(Modifier.weight(0.5f).fillMaxHeight(), contentAlignment = Alignment.CenterEnd){
            IconNav(
                text = LocalizedStringResource(R.string.navBar_home),
                icon = Icons.Outlined.Home,
                selected = btnSelected == 0,
            ) {
                onSelected(MainScreen)
            }
        }

        Box(Modifier.weight(0.4f).fillMaxHeight(), contentAlignment = Alignment.Center){
            IconNav(
                text = LocalizedStringResource(R.string.navBarQuizzes),
                icon = Icons.AutoMirrored.Outlined.FactCheck,
                selected = btnSelected == 1,
            ) {
                onSelected(MyQuizzesEsc)
            }
        }

        Box(Modifier.weight(0.4f).fillMaxHeight(), contentAlignment = Alignment.Center){
            IconNav(
                text = LocalizedStringResource(R.string.navBarDiscover),
                icon = Icons.Filled.Public,
                selected = btnSelected == 2,
            ) {
                onSelected(PublicQuizzesEsc)
            }
        }
        Box(Modifier.weight(0.5f).fillMaxHeight(), contentAlignment = Alignment.CenterStart){
            if(user?.photoUrl == "null"){
                IconNavLetter(
                    text = LocalizedStringResource(R.string.navBarperfil),
                    selected = btnSelected == 3,
                    letter = user.displayName?.firstOrNull()?.uppercase() ?: ""
                ) {
                    onSelected(PerfilESC)
                }

            }else{

                IconNav(
                    text = LocalizedStringResource(R.string.navBarperfil),
                    icon = Icons.Outlined.GroupAdd,
                    selected = btnSelected == 3,
                    isImage = true,
                    urlImage = user?.photoUrl.toString()
                ) {
                    onSelected(PerfilESC)
                }
            }

        }




    }
}