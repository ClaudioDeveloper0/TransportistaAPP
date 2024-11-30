package com.example.transportistaapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.transportistaapp.data.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.transportistaapp.data.FirebaseErrorUtils

class AuthViewModel : ViewModel() {
    private val authRepository = AuthRepository()

    // Estado del inicio de sesion
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    // Intentar iniciar sesión
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            try {

                val user = authRepository.login(email, password)

                if (user != null) {
                    _loginState.value = LoginState.Success(user.email)
                } else {
                    _loginState.value = LoginState.Failure("Usuario no encontrado")
                }
            } catch (e: Exception) {
                _loginState.value = LoginState.Failure(FirebaseErrorUtils.getErrorMessage(e))

            }
        }
    }
}

// Clases para representar el estado del login
sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val email: String?) : LoginState()
    data class Failure(val error: String) : LoginState()
}
