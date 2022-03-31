package cl.minkai.bloom.login.data.source

import cl.minkai.bloom.login.data.remote.model.RemoteAuthCredentials
import cl.minkai.bloom.login.data.remote.model.RemoteUserCredentialsParams

interface LoginRemote {
    suspend fun login(credentials: RemoteUserCredentialsParams): RemoteAuthCredentials
}