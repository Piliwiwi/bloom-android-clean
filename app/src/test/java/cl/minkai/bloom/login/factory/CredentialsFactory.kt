package cl.minkai.bloom.login.factory

import cl.minkai.bloom.login.data.remote.model.RemoteAuthCredentials
import cl.minkai.bloom.login.data.remote.model.RemoteUserCredentialsParams
import cl.minkai.bloom.login.presentation.login.model.UserCredentials
import cl.minkai.testingtools.factory.RandomFactory.generateString

object CredentialsFactory {
    fun makeRemoteUserCredentials() = RemoteUserCredentialsParams(
        email = generateString(),
        password = generateString(),
        getHash = true
    )

    fun makeRemoteAuthCredentials() = RemoteAuthCredentials(
        token = generateString()
    )

    fun makeUserCredentials() = UserCredentials(
        email = generateString(),
        password = generateString()
    )
}