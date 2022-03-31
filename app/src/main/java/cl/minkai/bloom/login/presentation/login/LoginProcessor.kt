package cl.minkai.bloom.login.presentation.login

import cl.minkai.bloom.login.data.LoginDataRepository
import cl.minkai.bloom.login.presentation.login.LoginAction.GoToForgotPasswordAction
import cl.minkai.bloom.login.presentation.login.LoginAction.HandleLoginAction
import cl.minkai.bloom.login.presentation.login.LoginAction.RenderUiAction
import cl.minkai.bloom.login.presentation.login.LoginResult.GoToForgotPasswordResult
import cl.minkai.bloom.login.presentation.login.LoginResult.HandLeLoginResult.InProgress
import cl.minkai.bloom.login.presentation.login.LoginResult.HandLeLoginResult.APIError
import cl.minkai.bloom.login.presentation.login.LoginResult.HandLeLoginResult.Success
import cl.minkai.bloom.login.presentation.login.LoginResult.RenderUiResult
import cl.minkai.bloom.login.presentation.login.mapper.CredentialsMapper
import cl.minkai.bloom.login.presentation.login.model.UserCredentials
import cl.minkai.mvi.execution.ExecutionThread
import cl.minkai.utils.model.NetworkError
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@FlowPreview
class LoginProcessor @Inject constructor(
    private val repository: LoginDataRepository,
    private val mapper: CredentialsMapper,
    private val executionThread: ExecutionThread
) {
    fun actionProcessor(action: LoginAction): Flow<LoginResult> =
        when (action) {
            RenderUiAction -> showUi()
            is HandleLoginAction -> handleLogin(action.credentials)
            GoToForgotPasswordAction -> goToForgotPassword()
        }

    private fun showUi() = flow<LoginResult> {
        emit(RenderUiResult)
    }

    private fun handleLogin(credentials: UserCredentials) = flow<LoginResult> {
        repository.login(with(mapper) { credentials.toRemote() }).collect { response ->
            emit(Success(response.token))
        }
    }.onStart {
        emit(InProgress)
    }.catch { cause ->
        emit(APIError(NetworkError(message = cause.message.orEmpty())))
    }.flowOn(executionThread.ioThread())

    private fun goToForgotPassword() = flow<LoginResult> {
        emit(GoToForgotPasswordResult)
    }
}