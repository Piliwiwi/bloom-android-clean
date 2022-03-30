package cl.minkai.bloom.login.data.source

interface LoginCache {
    suspend fun storeToken(token: String)
}