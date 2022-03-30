package cl.minkai.network.security

interface TokenManager {
    fun getToken(): String
    fun setToken(token: String)
    fun revoke()
    fun hasToken(): Boolean
    fun isValid(token: String): Boolean
}