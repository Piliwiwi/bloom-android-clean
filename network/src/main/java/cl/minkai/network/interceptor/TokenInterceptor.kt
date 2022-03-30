package cl.minkai.network.interceptor

import cl.minkai.network.security.TokenManager
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor constructor(private val tokenManager: TokenManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val headers = if (tokenManager.getToken().isNotEmpty()) {
            chain.request().headers
                .newBuilder()
                .add("Authorization", tokenManager.getToken())
                //.add("Version", BuildConfig.VERSION_APP)
                .build()
        } else {
            chain.request().headers
                .newBuilder()
                //.add("Version", BuildConfig.VERSION_APP)
                .build()
        }
        val request = chain.request().newBuilder().headers(headers).build()
        return chain.proceed(request)
    }
}