package cl.minkai.bloom.login.data

import cl.minkai.bloom.login.data.remote.model.RemoteAuthCredentials
import cl.minkai.bloom.login.data.remote.model.RemoteUserCredentialsParams
import cl.minkai.bloom.login.data.source.LoginCache
import cl.minkai.bloom.login.data.source.LoginRemote
import cl.minkai.bloom.login.factory.CredentialsFactory.makeRemoteAuthCredentials
import cl.minkai.bloom.login.factory.CredentialsFactory.makeRemoteUserCredentials
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertEquals

internal class LoginDataRepositoryTest {
    private val remote = mockk<LoginRemote>()
    private val cache = mockk<LoginCache>()

    private val repository = LoginDataRepository(remote, cache)

    @Test
    fun `given RemoteUserCredentialsParams, when login, then flow RemoteAuthCredentials`() =
        runBlocking {
            val request = makeRemoteUserCredentials()
            val response = makeRemoteAuthCredentials()

            stubRemoteLogin(request, response)
            stubCacheLogin(response.token)

            repository.login(request).collect { result ->
                assertEquals(response, result)
            }
        }

    private fun stubRemoteLogin(
        request: RemoteUserCredentialsParams,
        response: RemoteAuthCredentials
    ) = runBlocking {
        coEvery { remote.login(request) } returns response
    }

    private fun stubCacheLogin(token: String) = runBlocking {
        coEvery { cache.storeToken(token) } returns Unit
    }
}