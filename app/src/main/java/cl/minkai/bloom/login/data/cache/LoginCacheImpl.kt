package cl.minkai.bloom.login.data.cache

import cl.minkai.bloom.login.data.source.LoginCache
import cl.minkai.network.security.PassportTokenManager
import javax.inject.Inject

class LoginCacheImpl @Inject constructor(
    private val passportManager: PassportTokenManager
) : LoginCache {
    override suspend fun storeToken(token: String) {
        passportManager.setToken(token)
    }
}