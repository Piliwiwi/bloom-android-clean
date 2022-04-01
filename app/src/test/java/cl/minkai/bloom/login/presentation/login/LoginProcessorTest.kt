package cl.minkai.bloom.login.presentation.login

import cl.minkai.bloom.common.Config.GENERIC_NETWORK_ERROR_MESSAGE
import cl.minkai.bloom.factory.NetworkFactory.makeNetworkError
import cl.minkai.bloom.login.data.LoginDataRepository
import cl.minkai.bloom.login.data.remote.model.RemoteAuthCredentials
import cl.minkai.bloom.login.data.remote.model.RemoteUserCredentialsParams
import cl.minkai.bloom.login.factory.CredentialsFactory.makeRemoteAuthCredentials
import cl.minkai.bloom.login.factory.CredentialsFactory.makeRemoteUserCredentials
import cl.minkai.bloom.login.factory.CredentialsFactory.makeUserCredentials
import cl.minkai.bloom.login.presentation.login.LoginAction.GoToForgotPasswordAction
import cl.minkai.bloom.login.presentation.login.LoginAction.HandleLoginAction
import cl.minkai.bloom.login.presentation.login.LoginAction.RenderUiAction
import cl.minkai.bloom.login.presentation.login.LoginResult.GoToForgotPasswordResult
import cl.minkai.bloom.login.presentation.login.LoginResult.HandLeLoginResult.APIError
import cl.minkai.bloom.login.presentation.login.LoginResult.HandLeLoginResult.InProgress
import cl.minkai.bloom.login.presentation.login.LoginResult.HandLeLoginResult.Success
import cl.minkai.bloom.login.presentation.login.LoginResult.RenderUiResult
import cl.minkai.bloom.login.presentation.login.mapper.CredentialsMapper
import cl.minkai.bloom.login.presentation.login.model.UserCredentials
import cl.minkai.mvi.execution.ExecutionThreadEnvironment
import cl.minkai.mvi.execution.ExecutionThreadFactory
import cl.minkai.network.utils.NetworkError
import cl.minkai.network.utils.NetworkErrorHandler
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertTrue

@FlowPreview
internal class LoginProcessorTest {
    private val repository = mockk<LoginDataRepository>()
    private val mapper = mockk<CredentialsMapper>()
    private val errorHandler = mockk<NetworkErrorHandler>()
    private val executionThread = ExecutionThreadFactory.makeExecutionThread(
        ExecutionThreadEnvironment.TESTING
    )

    private val processor = LoginProcessor(
        repository = repository,
        mapper = mapper,
        errorHandler = errorHandler,
        executionThread = executionThread
    )

    @Test
    fun `given RenderUiAction, when actionProcessor, then RenderUiResult`() = runBlocking {
        val result = processor.actionProcessor(RenderUiAction).toList()

        val mapOutput = result.first()

        assertTrue(mapOutput is RenderUiResult)
    }

    @Test
    fun `given HandleLoginAction, when actionProcessor, then Success`() = runBlocking {
        val remoteRequest = makeRemoteUserCredentials()
        val response = makeRemoteAuthCredentials()
        val request = makeUserCredentials()

        stubMapper(request, remoteRequest)
        stubRepositoryLoginSuccess(remoteRequest, response)

        val result = processor.actionProcessor(HandleLoginAction(request)).toList()

        val mapOutput = result.last()

        assertTrue(mapOutput is Success)
    }

    @Test
    fun `given HandleLoginAction, when actionProcessor, then InProgress`() = runBlocking {
        val remoteRequest = makeRemoteUserCredentials()
        val response = makeRemoteAuthCredentials()
        val request = makeUserCredentials()

        stubMapper(request, remoteRequest)
        stubRepositoryLoginSuccess(remoteRequest, response)

        val result = processor.actionProcessor(HandleLoginAction(request)).toList()

        val mapOutput = result.first()

        assertTrue(mapOutput is InProgress)
    }

    @Test
    fun `given HandleLoginAction, when actionProcessor, then APIError`() = runBlocking {
        val remoteRequest = makeRemoteUserCredentials()
        val request = makeUserCredentials()
        val networkError = makeNetworkError()
        val error = Throwable()

        stubMapper(request, remoteRequest)
        stubNetworkErrorHandler(error, networkError)
        stubRepositoryLoginFailed(error)

        val result = processor.actionProcessor(HandleLoginAction(request)).toList()

        val mapOutput = result.last()

        assertTrue(mapOutput is APIError)
    }

    @Test
    fun `given GoToForgotPasswordAction, when actionProcessor, then GoToForgotPasswordResult`() =
        runBlocking {
            val result = processor.actionProcessor(GoToForgotPasswordAction).toList()

            val mapOutput = result.first()

            assertTrue(mapOutput is GoToForgotPasswordResult)
        }

    private fun stubMapper(presentation: UserCredentials, remote: RemoteUserCredentialsParams) {
        every { with(mapper) { presentation.toRemote() } } returns remote
    }

    private fun stubRepositoryLoginSuccess(
        request: RemoteUserCredentialsParams,
        response: RemoteAuthCredentials
    ) = runBlocking {
        coEvery { repository.login(request) } returns flow { emit(response) }
    }

    private fun stubRepositoryLoginFailed(cause: Throwable) = runBlocking {
        coEvery { repository.login(any()) } throws cause
    }

    private fun stubNetworkErrorHandler(cause: Throwable, networkError: NetworkError) {
        every {
            with(errorHandler) {
                cause.toNetworkError(GENERIC_NETWORK_ERROR_MESSAGE)
            }
        } returns networkError
    }
}