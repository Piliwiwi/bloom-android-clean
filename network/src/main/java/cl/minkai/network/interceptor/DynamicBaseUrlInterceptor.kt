package cl.minkai.network.interceptor

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Interceptor
import okhttp3.Response

class DynamicBaseUrlInterceptor : Interceptor {
    @Volatile
    var host: String? = null

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        host?.let { hostNotNull ->
            val newUrl: HttpUrl = hostNotNull.toHttpUrl()
            request = request.newBuilder().url(newUrl).build()
        }
        return chain.proceed(request)
    }
}