package cl.minkai.bloom.login.di

import android.content.Context
import cl.minkai.network.interceptor.InterceptorParams

data class LoginDependencies(
    val interceptorParams: InterceptorParams,
    val flavorName: String,
    val isDebug: Boolean,
    val context: Context
)