package com.yoinerdev.quizzesia.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomAlertDialog(
    showDialog: Boolean,
    title: String,
    messages: List<String>,
    onDismiss: () -> Unit,
    confirmAction: (() -> Unit)? = null,
    confirmText: String = "👌 Entendido",
    dismissAction: (() -> Unit)? = null,
    dismissText: String? = null
) {
    AnimatedVisibility(visible = showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = title, fontSize = 19.sp) },
            text = {
                Column {
                    messages.forEachIndexed { index, message ->
                        Text(message)
                        if (index < messages.size - 1) {
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { confirmAction?.invoke() ?: onDismiss() }) {
                    Text(confirmText)
                }
            },
            dismissButton = if (dismissAction != null && dismissText != null) {
                {
                    TextButton(onClick = dismissAction) {
                        Text(dismissText)
                    }
                }
            } else null
        )
    }
}

