package com.bolhy91.firebaseapp.presentation.auth.register

data class RegisterState(
    val isAuth: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)
