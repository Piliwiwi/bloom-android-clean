package cl.minkai.network.security

import android.content.SharedPreferences

class PassportTokenManager constructor(
    private val sharedPreferences: SharedPreferences
) : TokenManager {
    override fun getToken() = sharedPreferences.getString(KEY_PASSPORT_TOKEN, "").orEmpty()

    override fun setToken(token: String) {
        sharedPreferences.edit().putString(KEY_PASSPORT_TOKEN, token).apply()
    }

    override fun revoke() {
        sharedPreferences.edit().remove(KEY_PASSPORT_TOKEN).apply()
    }

    override fun hasToken() = sharedPreferences.contains(KEY_PASSPORT_TOKEN)

    override fun isValid(token: String): Boolean {
        if (token.isEmpty()) return false
        val currentToken = getToken()
        if (currentToken.isEmpty()) return false
        return token == currentToken
    }

    companion object {
        const val KEY_PASSPORT_TOKEN = "cl.minkai.passport.token"
    }
}