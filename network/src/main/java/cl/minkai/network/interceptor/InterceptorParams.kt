package cl.minkai.network.interceptor

import okhttp3.Interceptor

data class InterceptorParams(
    val tokenInterceptor: Interceptor,
    val unauthorizedInterceptor: Interceptor,
    val dynamicBaseUrlInterceptor: Interceptor? = null
)
