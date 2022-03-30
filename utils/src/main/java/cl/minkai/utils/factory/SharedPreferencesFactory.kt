package cl.minkai.utils.factory

import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesFactory {
    fun makeSharedPreferencesFactory(context: Context, name: String, mode: Int): SharedPreferences =
        context.getSharedPreferences(name, mode)
}