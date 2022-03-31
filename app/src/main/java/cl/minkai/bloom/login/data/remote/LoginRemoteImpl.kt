package cl.minkai.bloom.login.data.remote

import cl.minkai.bloom.login.data.remote.model.RemoteUserCredentialsParams
import cl.minkai.bloom.login.data.remote.retrofit.LoginWebServiceAPI
import cl.minkai.bloom.login.data.source.LoginRemote
import javax.inject.Inject

class LoginRemoteImpl @Inject constructor(private val api: LoginWebServiceAPI) : LoginRemote {
    override suspend fun login(credentials: RemoteUserCredentialsParams) = api.login(credentials)
}