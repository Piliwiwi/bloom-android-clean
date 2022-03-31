package cl.minkai.bloom.login.ui.di

import cl.minkai.bloom.login.data.remote.LoginRemoteImpl
import cl.minkai.bloom.login.data.remote.retrofit.LoginWebServiceAPI
import cl.minkai.bloom.login.data.source.LoginRemote
import cl.minkai.network.config.NetworkDependencies
import cl.minkai.network.retrofit.WebServiceFactory
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class RemoteModule {

    @Binds
    abstract fun bindRemote(remote: LoginRemoteImpl): LoginRemote

    @Module
    companion object {

        @Provides
        @JvmStatic
        fun providesWebServiceRetrofit(
            dependencies: NetworkDependencies
        ): LoginWebServiceAPI = WebServiceFactory(
            tClass = LoginWebServiceAPI::class.java,
            context = dependencies.context,
            isDebug = dependencies.isDebug,
            interceptorParams = dependencies.interceptorParams
        ).createWebServiceInstance(dependencies.flavorName)
    }
}