package cl.minkai.utils.factory

import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesFactory {
    private const val GENERAL_DOMAIN = "cl.utils.general.domain"

    fun makeGeneralSharedPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences(GENERAL_DOMAIN, Context.MODE_PRIVATE)

    fun makeDomainSharedPreferences(context: Context, domain: String): SharedPreferences =
        context.getSharedPreferences(domain, Context.MODE_PRIVATE)
}