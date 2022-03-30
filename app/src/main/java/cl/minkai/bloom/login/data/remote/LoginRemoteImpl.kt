package cl.minkai.bloom.login.data.remote

import cl.minkai.bloom.login.data.remote.model.UserCredentialsParams
import cl.minkai.bloom.login.data.remote.retrofit.LoginWebServiceAPI
import cl.minkai.bloom.login.data.source.LoginRemote
import javax.inject.Inject

class LoginRemoteImpl @Inject constructor(private val api: LoginWebServiceAPI) : LoginRemote {
    override suspend fun login(credentials: UserCredentialsParams) = api.login(credentials)
}