package com.yoinerdev.quizzesia.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.Visibility
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
import com.yoinerdev.quizzesia.presentation.ui.theme.BlackBackGround
import com.yoinerdev.quizzesia.presentation.ui.theme.MainOrange

@Composable
fun CustomTField(
    value: String,
    onValueChange: (String) -> Unit,
    label:String,
    shape:CornerBasedShape = ShapeDefaults.Small,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    leadingIcon : ImageVector,
    maxLines: Int = 1
) {

    OutlinedTextField(
        label = { Text(label) },
        onValueChange = { onValueChange(it) },
        value = value,
        maxLines = maxLines,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            focusedIndicatorColor = MainOrange,
            unfocusedLabelColor = Color.Gray,
            focusedLabelColor = Color.White,


        ),
        shape = shape,
        modifier = Modifier.fillMaxWidth(),
        visualTransformation = visualTransformation,
        leadingIcon = {
            Icon(
                leadingIcon,
                contentDescription = "icon",
                tint = BlackBackGround
            )
        }
    )
}