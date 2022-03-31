package cl.minkai.bloom.login.presentation.login

import cl.minkai.mvi.events.MviUiState

sealed class LoginUiState : MviUiState {
    object DefaultUiState : LoginUiState()
    object LoadingUiState : LoginUiState()
    object ShowLoginScreenUiState : LoginUiState()
}