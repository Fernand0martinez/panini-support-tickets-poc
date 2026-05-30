package com.panini.supporttickets.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.panini.supporttickets.core.UiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _loginState = MutableStateFlow<UiState<Unit>?>(null)
    val loginState: StateFlow<UiState<Unit>?> = _loginState.asStateFlow()

    fun login(email: String) {
        if (email.isBlank()) {
            _loginState.value = UiState.Error("Ingresa un correo corporativo para continuar.")
            return
        }

        viewModelScope.launch {
            _loginState.value = UiState.Loading
            delay(400)
            _loginState.value = UiState.Success(Unit)
        }
    }
}
