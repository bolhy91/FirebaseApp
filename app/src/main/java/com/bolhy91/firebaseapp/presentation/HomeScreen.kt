package com.bolhy91.firebaseapp.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val currentUser = viewModel.currentUser
    Column {
        Text(text = "Hola Bienvenido al Home")
        currentUser.value?.email?.let { Text(text = it) }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}