package com.bolhy91.firebaseapp.presentation.auth.register

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bolhy91.firebaseapp.domain.repository.AuthRepository
import com.bolhy91.firebaseapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _state: MutableState<RegisterState> = mutableStateOf(RegisterState())
    val state: State<RegisterState> = _state

    private fun registerUser(email: String, password: String) {
        viewModelScope.launch {
            authRepository.register(email, password)
                .collect { result ->
                    when (result) {
                        is Resource.Error -> {

                        }
                        is Resource.Loading -> {
                            _state.value = _state.value.copy(
                                isLoading = result.isLoading,
                                error = null
                            )
                        }
                        is Resource.Success -> {

                        }
                    }
                }
        }
    }
}