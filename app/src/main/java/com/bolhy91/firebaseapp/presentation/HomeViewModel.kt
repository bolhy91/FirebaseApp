package com.bolhy91.firebaseapp.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bolhy91.firebaseapp.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _firebaseUser: MutableState<FirebaseUser?> = mutableStateOf(null)
    val currentUser: State<FirebaseUser?> = _firebaseUser
    val isAuthenticatedUser = authRepository.isAuthenticatedInFirebase()

    init {
        getCurrentUser()
    }

    private fun getCurrentUser() {
        viewModelScope.launch {
            val user = authRepository.getCurrentUser()
            _firebaseUser.value = user
        }
    }
}