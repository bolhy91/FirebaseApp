package com.bolhy91.firebaseapp.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bolhy91.firebaseapp.ui.theme.FirebaseAppTheme
import com.bolhy91.firebaseapp.utils.ScopeManager
import com.bolhy91.firebaseapp.utils.Toaster
import com.bolhy91.firebaseapp.utils.forward

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onLogin: () -> Unit,
    onAddTask: () -> Unit
) {
    val currentUser = viewModel.currentUser
    val context = LocalContext.current

    LaunchedEffect(key1 = viewModel.uiScope.value) {
        viewModel.uiScope.forward(
            ScopeManager(
                toaster = Toaster(context)
            )
        )
    }

    Scaffold(
        topBar = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.primary)
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Firebase App",
                    style = MaterialTheme.typography.h2,
                )
                IconButton(onClick = {
                    viewModel.logout()
                    onLogin()
                }) {
                    Icon(
                        Icons.Filled.ExitToApp,
                        contentDescription = "logout",
                        modifier = Modifier
                            .size(30.dp),
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text(text = "Add") },
                onClick = { onAddTask() },
                elevation = FloatingActionButtonDefaults.elevation(8.dp),
                icon = {
                    Icon(
                        Icons.Filled.Add,
                        contentDescription = "Add task"
                    )
                })
        },
    ) {
        Column {
            currentUser.value?.email?.let {
                Text(
                    text = "Authenticated user: $it",
                    modifier = Modifier
                        .padding(16.dp),
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold,
                    softWrap = true
                )
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    FirebaseAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            HomeScreen(onLogin = {}, onAddTask = {})
        }
    }
}