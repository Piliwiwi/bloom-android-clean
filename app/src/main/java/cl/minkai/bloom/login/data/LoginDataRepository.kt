package cl.minkai.bloom.login.data

import cl.minkai.bloom.login.data.remote.model.UserCredentialsParams
import cl.minkai.bloom.login.data.source.LoginRemote
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginDataRepository @Inject constructor(private val remote: LoginRemote) {
    fun login(credentials: UserCredentialsParams) = flow {
        val auth = remote.login(credentials)
        emit(auth)
    }
}