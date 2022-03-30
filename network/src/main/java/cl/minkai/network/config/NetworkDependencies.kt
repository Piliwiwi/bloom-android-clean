package cl.minkai.network.config

import android.content.Context
import cl.minkai.network.interceptor.InterceptorParams

data class NetworkDependencies(
    val interceptorParams: InterceptorParams,
    val flavorName: String,
    val isDebug: Boolean,
    val context: Context
)