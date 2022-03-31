package cl.minkai.bloom.login.presentation.login

import cl.minkai.bloom.login.presentation.login.LoginResult.GoToForgotPasswordResult
import cl.minkai.bloom.login.presentation.login.LoginResult.HandLeLoginResult.APIError
import cl.minkai.bloom.login.presentation.login.LoginResult.HandLeLoginResult.InProgress
import cl.minkai.bloom.login.presentation.login.LoginResult.HandLeLoginResult.Success
import cl.minkai.bloom.login.presentation.login.LoginResult.RenderUiResult
import cl.minkai.bloom.login.presentation.login.LoginUiState.DefaultUiState
import cl.minkai.bloom.login.presentation.login.LoginUiState.LoadingUiState
import cl.minkai.bloom.login.presentation.login.LoginUiState.ShowLoginScreenUiState
import cl.minkai.mvi.MviReducer
import cl.minkai.mvi.UnsupportedReduceException
import javax.inject.Inject

class LoginReducer @Inject constructor() : MviReducer<LoginUiState, LoginResult> {
    override fun LoginUiState.reduceWith(result: LoginResult): LoginUiState {
        return when (val currentState = this) {
            is DefaultUiState -> currentState reduceWith result
            is LoadingUiState -> currentState reduceWith result
            is ShowLoginScreenUiState -> currentState reduceWith result
        }
    }

    infix fun DefaultUiState.reduceWith(result: LoginResult): LoginUiState {
        return when (result) {
            RenderUiResult -> ShowLoginScreenUiState
            else -> throw UnsupportedReduceException(this, result)
        }
    }

    infix fun LoadingUiState.reduceWith(result: LoginResult): LoginUiState {
        return when (result) {
            is Success -> ShowLoginScreenUiState
            is APIError -> ShowLoginScreenUiState
            else -> throw UnsupportedReduceException(this, result)
        }
    }

    infix fun ShowLoginScreenUiState.reduceWith(result: LoginResult): LoginUiState {
        return when (result) {
            InProgress -> LoadingUiState
            GoToForgotPasswordResult -> this
            else -> throw UnsupportedReduceException(this, result)
        }
    }
}