package com.bolhy91.firebaseapp.domain.model

data class Task(
    val title: String = "",
    val description: String,
    val isCheck: Boolean,
)