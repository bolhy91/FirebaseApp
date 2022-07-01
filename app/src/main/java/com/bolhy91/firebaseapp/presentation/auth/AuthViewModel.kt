package com.bolhy91.firebaseapp.presentation.auth

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bolhy91.firebaseapp.domain.repository.AuthRepository
import com.bolhy91.firebaseapp.utils.Destination
import com.bolhy91.firebaseapp.utils.Resource
import com.bolhy91.firebaseapp.utils.UIScope
import com.bolhy91.firebaseapp.utils.scope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val EMAIL_REGEX = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"

    private val _state: MutableState<RegisterState> = mutableStateOf(RegisterState())
    val state: State<RegisterState> = _state

    val uiScope = mutableStateOf<UIScope?>(null)

    val isAuthenticatedUser = authRepository.isAuthenticatedInFirebase()

    fun registerUser(email: String, password: String) {
        viewModelScope.launch {
            authRepository.register(email, password)
                .collect { result ->
                    when (result) {
                        is Resource.Error -> {
                            _state.value = _state.value.copy(
                                error = result.message,
                                isAuth = false,
                                isLoading = false
                            )
                            withContext(Dispatchers.Main) {
                                uiScope.scope {
                                    it.toaster?.toast(result.message ?: "Error: Connection")
                                }
                            }
                        }
                        is Resource.Loading -> {
                            _state.value = _state.value.copy(
                                isLoading = result.isLoading,
                                error = null,
                            )
                        }
                        is Resource.Success -> {
                            result.data?.let {
                                _state.value = _state.value.copy(
                                    isAuth = result.data,
                                    isLoading = false,
                                    error = null
                                )
                            }
                            withContext(Dispatchers.Main){
                                uiScope.scope {
                                    it.navHostController?.navigate(Destination.Home.route)
                                }
                            }
                        }
                    }
                }
        }
    }

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            authRepository.login(email, password).collect { result ->
                when (result) {
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            isAuth = false,
                            error = result.message
                        )
                        withContext(Dispatchers.Main) {
                            uiScope.scope {
                                it.toaster?.toast(result.message ?: "Error connection")
                            }
                        }
                    }
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            isLoading = result.isLoading,
                            error = result.message
                        )
                    }
                    is Resource.Success -> {
                        result.data?.let {
                            _state.value = _state.value.copy(
                                isAuth = result.data,
                                isLoading = false,
                                error = null
                            )
                        }
                        withContext(Dispatchers.Main){
                            uiScope.scope {
                                it.navHostController?.navigate(Destination.Home.route)
                            }
                        }
                    }
                }
            }
        }
    }

    fun validateForm(email: MutableState<String>, password: MutableState<String>): Boolean {
        return if ((email.value.isEmpty() && !EMAIL_REGEX.toRegex().matches(email.value))
        ) {
            false
        } else !(password.value.isEmpty() && password.value.length < 8)
    }
}