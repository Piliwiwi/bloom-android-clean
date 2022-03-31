package cl.minkai.bloom.common.factory

import android.content.Context
import cl.minkai.bloom.BuildConfig
import cl.minkai.network.config.NetworkDependencies
import cl.minkai.network.interceptor.AnyInterceptor
import cl.minkai.network.interceptor.InterceptorParams
import cl.minkai.network.interceptor.TokenInterceptor
import cl.minkai.network.security.TokenManager

object NetworkDependenciesFactory {
    fun makeNetworkDependencies(context: Context, tokenManager: TokenManager): NetworkDependencies {

        return NetworkDependencies(
            interceptorParams = InterceptorParams(
                tokenInterceptor = TokenInterceptor(tokenManager),
                unauthorizedInterceptor = AnyInterceptor()
            ),
            flavorName = BuildConfig.FLAVOR,
            isDebug = BuildConfig.DEBUG,
            context = context
        )
    }
}