package cl.minkai.bloom.login.presentation.login.mapper

import cl.minkai.bloom.login.data.remote.model.RemoteUserCredentialsParams
import cl.minkai.bloom.login.presentation.login.model.UserCredentials
import javax.inject.Inject

class CredentialsMapper @Inject constructor() {
    fun UserCredentials.toRemote() = RemoteUserCredentialsParams(
        email = email,
        password = password,
        getHash = true
    )
}