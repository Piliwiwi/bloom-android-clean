package cl.minkai.utils.session

import android.content.SharedPreferences
import cl.minkai.network.security.PassportTokenManager
import javax.inject.Inject

class SessionHelper @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val onChangeSessionListener: OnChangeSessionListener,
    private val passportTokenManager: PassportTokenManager
) {

    fun exitToLogin() {
        clearUserInfo()
        invalidateSession()
        onChangeSessionListener.onReLoginUser()
    }

    fun changeUser() {
        onChangeSessionListener.onChangeUser()
    }

    fun recoverPassword() {
        onChangeSessionListener.onRecoveryUserPassword()
    }

    fun getToken() = passportTokenManager.getToken()

    fun invalidateSession() {
        sharedPreferences.edit().remove(KEY_TOKEN).apply()
        passportTokenManager.revoke()
    }

    private fun clearUserInfo() {
        sharedPreferences.edit().remove(KEY_NAME).apply()
        sharedPreferences.edit().remove(KEY_NUMBER).apply()
        sharedPreferences.edit().remove(KEY_TOKEN).apply()
    }

    companion object {
        const val KEY_NAME = "name"
        const val KEY_NUMBER = "number"
        const val KEY_TOKEN = "api_token"
    }
}