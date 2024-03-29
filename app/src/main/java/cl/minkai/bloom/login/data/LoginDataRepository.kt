package cl.minkai.bloom.login.data

import cl.minkai.bloom.login.data.remote.model.RemoteUserCredentialsParams
import cl.minkai.bloom.login.data.source.LoginCache
import cl.minkai.bloom.login.data.source.LoginRemote
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginDataRepository @Inject constructor(
    private val remote: LoginRemote,
    private val cache: LoginCache
) {
    fun login(credentials: RemoteUserCredentialsParams) = flow {
        val auth = remote.login(credentials)
        cache.storeToken(auth.token)
        FirebaseCrashlytics.getInstance().setUserId(credentials.email)
        emit(auth)
    }
}