package cl.minkai.bloom.login.presentation.login

import cl.minkai.bloom.login.presentation.login.model.UserCredentials
import cl.minkai.mvi.events.MviAction

sealed class LoginAction : MviAction {
    object RenderUiAction : LoginAction()
    data class HandleLoginAction(val credentials: UserCredentials) : LoginAction()
    object GoToForgotPasswordAction : LoginAction()
}