package com.security.zarpay.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.security.zarpay.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Success : AuthState()
    data class Error(val message: String) : AuthState()
}

class AuthViewModel : ViewModel() {

    private val repository = AuthRepository()

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    fun signUp(name: String, email: String, password: String, mpin: String) {
        // Basic validation
        if (name.isBlank() || email.isBlank() || password.isBlank() || mpin.length != 4) {
            _authState.value = AuthState.Error("Please fill all fields correctly")
            return
        }

        _authState.value = AuthState.Loading
        viewModelScope.launch {
            val result = repository.signUp(name, email, password, mpin)
            result.onSuccess {
                _authState.value = AuthState.Success
            }.onFailure { e ->
                _authState.value = AuthState.Error(e.message ?: "Signup failed")
            }
        }
    }

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _authState.value = AuthState.Error("Please enter email and password")
            return
        }

        _authState.value = AuthState.Loading
        viewModelScope.launch {
            val result = repository.login(email, password)
            result.onSuccess {
                _authState.value = AuthState.Success
            }.onFailure { e ->
                _authState.value = AuthState.Error(e.message ?: "Login failed")
            }
        }
    }

    fun getCurrentUserId(): String? {
        return repository.getCurrentUserId()
    }

    fun resetState() {
        _authState.value = AuthState.Idle
    }
}

