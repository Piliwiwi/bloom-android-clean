package cl.minkai.bloom.login.presentation.login

import cl.minkai.mvi.events.MviResult
import cl.minkai.utils.model.NetworkError

sealed class LoginResult : MviResult {
    object RenderUiResult : LoginResult()

    sealed class HandLeLoginResult : LoginResult() {
        object InProgress : HandLeLoginResult()
        object InvalidCredentials : HandLeLoginResult()
        data class APIError(val error: NetworkError) : HandLeLoginResult()
        data class Success(val token: String) : HandLeLoginResult()
    }

    object GoToForgotPasswordResult : LoginResult()
}