package cl.minkai.bloom.login.presentation.login

import cl.minkai.bloom.login.presentation.login.model.UserCredentials
import cl.minkai.mvi.events.MviUserIntent

sealed class LoginUIntent: MviUserIntent {
    object InitialUIntent: LoginUIntent()
    data class LoggingUIntent(val credentials: UserCredentials): LoginUIntent()
    object ForgotPasswordUIntent: LoginUIntent()
}