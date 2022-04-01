package cl.minkai.bloom.login.presentation.login

import cl.minkai.bloom.factory.NetworkFactory.makeNetworkError
import cl.minkai.bloom.login.presentation.login.LoginResult.GoToForgotPasswordResult
import cl.minkai.bloom.login.presentation.login.LoginResult.HandLeLoginResult.InProgress
import cl.minkai.bloom.login.presentation.login.LoginResult.RenderUiResult
import cl.minkai.bloom.login.presentation.login.LoginUiState.DefaultUiState
import cl.minkai.bloom.login.presentation.login.LoginUiState.LoadingUiState
import cl.minkai.bloom.login.presentation.login.LoginUiState.ShowLoginScreenUiState
import cl.minkai.testingtools.factory.RandomFactory.generateString
import org.junit.Test

internal class LoginReducerTest {
    private val reducer = LoginReducer()

    @Test
    fun `given DefaultUiState, when RenderUiResult, then ShowLoginScreenUiState`() {
        val currentState = DefaultUiState
        val result = RenderUiResult

        val nextState = with(reducer) { currentState reduceWith result }

        assert(nextState is ShowLoginScreenUiState)
    }

    @Test
    fun `given LoadingUiState, when result-Success, then ShowLoginScreenUiState`() {
        val currentState = LoadingUiState
        val token = generateString()
        val result = LoginResult.HandLeLoginResult.Success(token)

        val nextState = with(reducer) { currentState reduceWith result }

        assert(nextState is ShowLoginScreenUiState)
    }

    @Test
    fun `given LoadingUiState, when result-APIError, then ShowLoginScreenUiState`() {
        val currentState = LoadingUiState
        val networkError = makeNetworkError()
        val result = LoginResult.HandLeLoginResult.APIError(networkError)

        val nextState = with(reducer) { currentState reduceWith result }

        assert(nextState is ShowLoginScreenUiState)
    }

    @Test
    fun `given ShowLoginScreenUiState, when result-InProgress, then LoadingUiState`() {
        val currentState = ShowLoginScreenUiState
        val result = InProgress

        val nextState = with(reducer) { currentState reduceWith result }

        assert(nextState is LoadingUiState)
    }

    @Test
    fun `given ShowLoginScreenUiState, when GoToForgotPasswordResult, then ShowLoginScreenUiState`() {
        val currentState = ShowLoginScreenUiState
        val result = GoToForgotPasswordResult

        val nextState = with(reducer) { currentState reduceWith result }

        assert(nextState is ShowLoginScreenUiState)
    }
}