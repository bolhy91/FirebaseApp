package com.bolhy91.firebaseapp.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bolhy91.firebaseapp.domain.repository.AuthRepository
import com.bolhy91.firebaseapp.presentation.auth.LogoutState
import com.bolhy91.firebaseapp.utils.Resource
import com.bolhy91.firebaseapp.utils.UIScope
import com.bolhy91.firebaseapp.utils.scope
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
    val uiScope = mutableStateOf<UIScope?>(null)

    private val _logoutState: MutableState<LogoutState> = mutableStateOf(LogoutState())
    val logoutState: State<LogoutState> = _logoutState

    init {
        getCurrentUser()
    }

    private fun getCurrentUser() {
        viewModelScope.launch {
            val user = authRepository.getCurrentUser()
            _firebaseUser.value = user
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout().collect { r ->
                when (r) {
                    is Resource.Error -> {
                        uiScope.scope {
                            it.toaster?.toast(r.message ?: "Error firebase")
                        }
                    }
                    is Resource.Loading -> {
                        _logoutState.value = _logoutState.value.copy(
                            loading = r.isLoading
                        )
                    }
                    is Resource.Success -> {
                        r.data?.let {
                            _logoutState.value = _logoutState.value.copy(
                                loading = false,
                                success = r.data
                            )
                        }
                    }
                }
            }
        }
    }
}