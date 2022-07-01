package com.bolhy91.firebaseapp.presentation.task

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bolhy91.firebaseapp.presentation.HomeViewModel
import com.bolhy91.firebaseapp.ui.components.InputText
import com.bolhy91.firebaseapp.ui.theme.FirebaseAppTheme

@Composable
fun AddTaskScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onBackHome: () -> Unit,
) {

    val title = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        TopBar(onBackHome)
        InputText(placeholder = "Title", valueText = title, onChangeText = { title.value = it })
        InputText(
            placeholder = "Description",
            valueText = description,
            onChangeText = { description.value = it })
        TextButton(
            onClick = {
                if (!validationForm(title.value, description.value)) {
                    Toast.makeText(context, "Required fields", Toast.LENGTH_LONG)
                        .show()
                }
            },
            enabled = validationForm(title.value, description.value),
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.secondary)
        ) {
            Text(text = "Add Task")
        }
    }
}

@Composable
fun TopBar(onBackHome: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, start = 5.dp, end = 5.dp, bottom = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            Icons.Default.ArrowBack,
            contentDescription = "Go Home",
            modifier = Modifier
                .size(30.dp)
                .clickable { onBackHome() }
        )
        Text(text = "Add New Task", style = MaterialTheme.typography.h2)
    }
}

fun validationForm(title: String, description: String): Boolean {
    return title.isNotEmpty() && description.isNotEmpty()
}

@Preview
@Composable
fun AddTaskScreenPreview() {
    FirebaseAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            //   AddTaskScreen(v)
        }
    }
}