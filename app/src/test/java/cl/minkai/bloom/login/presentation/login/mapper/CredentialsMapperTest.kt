package cl.minkai.bloom.login.presentation.login.mapper

import cl.minkai.bloom.login.data.remote.model.RemoteUserCredentialsParams
import cl.minkai.bloom.login.factory.CredentialsFactory.makeUserCredentials
import cl.minkai.bloom.login.presentation.login.model.UserCredentials
import org.junit.Test
import kotlin.test.assertEquals

internal class CredentialsMapperTest {
    private val mapper = CredentialsMapper()

    @Test
    fun `given UserCredentials, when toRemote, then RemoteUserCredentialsParams`() {
        val user = makeUserCredentials()

        val remoteUser = with(mapper) {
            user.toRemote()
        }

        assertUserEquals(user, remoteUser)
    }

    private fun assertUserEquals(user: UserCredentials, remoteUser: RemoteUserCredentialsParams) {
        assertEquals(user.email, remoteUser.email)
        assertEquals(user.password, remoteUser.password)
        assertEquals(true, remoteUser.getHash)
    }
}