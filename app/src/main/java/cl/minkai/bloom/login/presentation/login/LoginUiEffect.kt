package cl.minkai.bloom.login.presentation.login

import cl.minkai.mvi.events.MviEffect
import cl.minkai.network.utils.NetworkError

sealed class LoginUiEffect : MviEffect {
    data class NetworkErrorUiEffect(val error: NetworkError) : LoginUiEffect()
    data class LoginSucceedUiEffect(val token: String) : LoginUiEffect()
}