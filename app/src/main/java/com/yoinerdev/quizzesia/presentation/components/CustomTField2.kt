package com.yoinerdev.quizzesia.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.yoinerdev.quizzesia.presentation.ui.theme.BlackBackGround
import com.yoinerdev.quizzesia.presentation.ui.theme.MainOrange

@Composable
fun CustomTField2(
    value: String,
    onValueChange: (String) -> Unit,
    label:String,
    shape:CornerBasedShape = ShapeDefaults.Small,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    leadingIcon : ImageVector,
    trailingIcon:ImageVector,
    iconClickedAfter: ImageVector = Icons.Outlined.VisibilityOff,
    showIcon: Boolean = false,
    enabled: Boolean = true,
    onIconClicked:()->Unit
) {

    var visibility by remember { mutableStateOf(true) }

    OutlinedTextField(
        label = { Text(label) },
        onValueChange = { onValueChange(it) },
        value = value,
        singleLine = true,
        enabled = enabled,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            focusedIndicatorColor = MainOrange,
            unfocusedLabelColor = Color.Gray,
            focusedLabelColor = Color.White,
            disabledContainerColor = Color.White,
            disabledLabelColor = Color.White,
            disabledTextColor = Color.Black


        ),
        shape = shape,
        modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp),
        visualTransformation = if(visibility) visualTransformation else VisualTransformation.None,
        leadingIcon = {
            Icon(
                leadingIcon,
                contentDescription = "icon",
                tint = BlackBackGround
            )
        },
        trailingIcon = {
            if(visualTransformation != VisualTransformation.None || showIcon){
                Icon(
                    if(visibility) trailingIcon else iconClickedAfter,
                    contentDescription = "icon",
                    tint = BlackBackGround,
                    modifier = Modifier.clickable {
                        visibility = !visibility
                        onIconClicked()
                    }
                )
            }

        }
    )
}