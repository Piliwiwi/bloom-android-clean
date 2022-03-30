package cl.minkai.bloom.login.data.source

import cl.minkai.bloom.login.data.remote.model.AuthCredentials
import cl.minkai.bloom.login.data.remote.model.UserCredentialsParams

interface LoginRemote {
    suspend fun login(credentials: UserCredentialsParams): AuthCredentials
}