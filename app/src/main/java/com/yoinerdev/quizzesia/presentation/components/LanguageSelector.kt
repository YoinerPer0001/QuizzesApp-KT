package com.yoinerdev.quizzesia.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.yoinerdev.quizzesia.core.helpers.getSavedLanguage
import com.yoinerdev.quizzesia.domain.model.CategoryMN
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@SuppressLint("ProduceStateDoesNotAssignValue")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSelector(
    label:String,
    allLanguages: List<Pair<String, String>>,
    onSelectedLan:(String,String)->Unit,
) {
    val context = LocalContext.current

    var expanded by remember { mutableStateOf(false) }
    var selectedLan by remember { mutableStateOf("") }

    // 🔎 Filtramos la lista según lo que se escribe
    val filteredLanguages by remember(selectedLan) {
        derivedStateOf {
            allLanguages.filter {
                it.second.contains(selectedLan, ignoreCase = true)
            }
        }
    }


    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        Modifier.padding(vertical = 5.dp)
    ) {

        // 🔥 TextField que actúa como input
        TextField(
            value = selectedLan,
            maxLines = 1,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                unfocusedLabelColor = Color.Black,
                focusedContainerColor = Color.White,
                focusedLabelColor = Color.Black,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
            shape = ShapeDefaults.Medium,
            onValueChange = {
                selectedLan = it
                expanded = true
                            },
            readOnly = false,
            label = { Text(label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .menuAnchor(MenuAnchorType.PrimaryEditable, true) // 👈 ancla el menú al input
                .fillMaxWidth()
        )

        // 🔥 Dropdown con scroll si la lista es larga
        ExposedDropdownMenu(
            expanded = expanded && filteredLanguages.isNotEmpty(),
            onDismissRequest = { expanded = false },
            Modifier.height(200.dp)
        ) {
            filteredLanguages.forEach { lang ->
                DropdownMenuItem(
                    text = { Text(lang.second) },
                    onClick = {
                        expanded = false
                        selectedLan = lang.second
                        onSelectedLan(lang.first, lang.second) //devuelve el id
                    }
                )
            }
        }
    }
}
