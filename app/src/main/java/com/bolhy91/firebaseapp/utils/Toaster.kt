package com.bolhy91.firebaseapp.utils

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController

data class Toaster(private val context: Context) {

    fun toast(message: String, duration: Int = Toast.LENGTH_LONG) {
        Toast.makeText(context, message, duration).show()
    }
}

typealias UIScope = suspend (ScopeManager) -> Unit

data class ScopeManager(
    val toaster: Toaster? = null,
    val navHostController: NavHostController? = null
)

fun MutableState<UIScope?>.scope(block: UIScope?) {
    this.value = { scopeManager ->
        block?.invoke(scopeManager)
        this.value = null
    }
}

suspend fun MutableState<UIScope?>.forward(scopeManager: ScopeManager) {
    this.value?.invoke(scopeManager)
}




