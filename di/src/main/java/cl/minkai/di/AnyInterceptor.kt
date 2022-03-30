package cl.minkai.di

import okhttp3.Interceptor
import okhttp3.Response

class AnyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        println("Any Interceptor")
        val request = chain.request()
        return chain.proceed(request)
    }
}