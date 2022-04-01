package cl.minkai.bloom.login.data.remote

import cl.minkai.bloom.login.data.remote.model.RemoteAuthCredentials
import cl.minkai.bloom.login.data.remote.model.RemoteUserCredentialsParams
import cl.minkai.bloom.login.data.remote.retrofit.LoginWebServiceAPI
import cl.minkai.bloom.login.factory.CredentialsFactory.makeRemoteAuthCredentials
import cl.minkai.bloom.login.factory.CredentialsFactory.makeRemoteUserCredentials
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertEquals

internal class LoginRemoteImplTest {
    private val api = mockk<LoginWebServiceAPI>()

    private val remote = LoginRemoteImpl(api)

    @Test
    fun `given RemoteUserCredentialsParams, when login, then RemoteAuthCredentials`() =
        runBlocking {
            val request = makeRemoteUserCredentials()
            val response = makeRemoteAuthCredentials()

            stubAPILogin(request, response)

            val result = remote.login(request)

            assertEquals(response, result)
        }

    private fun stubAPILogin(
        request: RemoteUserCredentialsParams,
        response: RemoteAuthCredentials
    ) = runBlocking {
        coEvery { api.login(request) } returns response
    }
}