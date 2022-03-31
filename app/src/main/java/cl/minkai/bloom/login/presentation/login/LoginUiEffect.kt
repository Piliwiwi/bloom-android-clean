package cl.minkai.bloom.login.presentation.login

import cl.minkai.mvi.events.MviEffect
import cl.minkai.utils.model.NetworkError

sealed class LoginUiEffect : MviEffect {
    object InvalidCredentialsUiEffect : LoginUiEffect()
    data class NetworkErrorUiEffect(val error: NetworkError) : LoginUiEffect()
    data class LoginSucceedUiEffect(val token: String) : LoginUiEffect()
}