package cl.minkai.bloom.login.presentation

import androidx.lifecycle.ViewModel
import cl.minkai.bloom.login.presentation.login.LoginAction.GoToForgotPasswordAction
import cl.minkai.bloom.login.presentation.login.LoginAction.HandleLoginAction
import cl.minkai.bloom.login.presentation.login.LoginAction.RenderUiAction
import cl.minkai.bloom.login.presentation.login.LoginProcessor
import cl.minkai.bloom.login.presentation.login.LoginReducer
import cl.minkai.bloom.login.presentation.login.LoginResult
import cl.minkai.bloom.login.presentation.login.LoginResult.HandLeLoginResult.APIError
import cl.minkai.bloom.login.presentation.login.LoginResult.HandLeLoginResult.InvalidCredentials
import cl.minkai.bloom.login.presentation.login.LoginResult.HandLeLoginResult.Success
import cl.minkai.bloom.login.presentation.login.LoginUIntent
import cl.minkai.bloom.login.presentation.login.LoginUIntent.ForgotPasswordUIntent
import cl.minkai.bloom.login.presentation.login.LoginUIntent.InitialUIntent
import cl.minkai.bloom.login.presentation.login.LoginUIntent.LoggingUIntent
import cl.minkai.bloom.login.presentation.login.LoginUiEffect
import cl.minkai.bloom.login.presentation.login.LoginUiEffect.InvalidCredentialsUiEffect
import cl.minkai.bloom.login.presentation.login.LoginUiEffect.LoginSucceedUiEffect
import cl.minkai.bloom.login.presentation.login.LoginUiEffect.NetworkErrorUiEffect
import cl.minkai.bloom.login.presentation.login.LoginUiState
import cl.minkai.bloom.login.presentation.login.LoginUiState.DefaultUiState
import cl.minkai.mvi.MviPresentation
import cl.minkai.mvi.MviPresentationEffect
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.scan
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
class LoginViewModel @Inject constructor(
    private val reducer: LoginReducer,
    private val processor: LoginProcessor
) : ViewModel(),
    MviPresentation<LoginUIntent, LoginUiState>,
    MviPresentationEffect<LoginUiEffect> {

    private val defaultUiState: LoginUiState = DefaultUiState
    private val uiEffect: MutableSharedFlow<LoginUiEffect> = MutableSharedFlow()

    override fun processUserIntentsAndObserveUiStates(userIntents: Flow<LoginUIntent>)
            : Flow<LoginUiState> =
        userIntents
            .buffer()
            .flatMapMerge { userIntent ->
                processor.actionProcessor(userIntent.toAction())
            }
            .handleEffect()
            .scan(defaultUiState) { currentState, result ->
                with(reducer) { currentState reduceWith result }
            }

    private fun LoginUIntent.toAction() =
        when (this) {
            InitialUIntent -> RenderUiAction
            is LoggingUIntent -> HandleLoginAction(credentials)
            ForgotPasswordUIntent -> GoToForgotPasswordAction
        }

    private fun Flow<LoginResult>.handleEffect(): Flow<LoginResult> =
        onEach { change ->
            val event = when (change) {
                InvalidCredentials -> InvalidCredentialsUiEffect
                is APIError -> NetworkErrorUiEffect(change.error)
                is Success -> LoginSucceedUiEffect(change.token)
                else -> return@onEach
            }
            uiEffect.emit(event)
        }

    override fun uiEffect() = uiEffect
}