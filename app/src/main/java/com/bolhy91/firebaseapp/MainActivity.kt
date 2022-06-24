package com.bolhy91.firebaseapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bolhy91.firebaseapp.presentation.HomeScreen
import com.bolhy91.firebaseapp.presentation.auth.AuthViewModel
import com.bolhy91.firebaseapp.presentation.auth.LoginScreen
import com.bolhy91.firebaseapp.presentation.auth.RegisterScreen
import com.bolhy91.firebaseapp.ui.theme.FirebaseAppTheme
import com.bolhy91.firebaseapp.utils.Destination
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<AuthViewModel>()
    private lateinit var navHostController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirebaseAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    navHostController = rememberNavController()
                    NavHost(
                        navController = navHostController,
                        startDestination = Destination.LoginNav.route
                    ) {
                        loginNav(navHostController)
                        registerNav(navHostController)
                        homeNav(navHostController)
                    }
                    checkAuthStatus()
                }
            }
        }
    }

    private fun checkAuthStatus() {
        if (viewModel.isAuthenticatedUser) {
            navHostController.navigate(Destination.Home.route)
        }
    }
}

fun NavGraphBuilder.loginNav(navHostController: NavHostController) {
    composable(Destination.LoginNav.route) {
        LoginScreen(
            onHomeNav = {
                navHostController.navigate(Destination.Home.route)
            },
            onRegisterNav = {
                navHostController.navigate(Destination.RegisterNav.route)
            }
        )
    }
}

fun NavGraphBuilder.registerNav(navHostController: NavHostController) {
    composable(Destination.RegisterNav.route) {
        RegisterScreen(
            onHomeNav = {
                navHostController.navigate(Destination.Home.route)
            },
            onLoginNav = {
                navHostController.navigate(Destination.LoginNav.route)
            }
        )
    }
}

fun NavGraphBuilder.homeNav(navHostController: NavHostController) {
    composable(Destination.Home.route) {
        HomeScreen(onLogin = {
            navHostController.navigate(Destination.LoginNav.route)
        })
    }
}
