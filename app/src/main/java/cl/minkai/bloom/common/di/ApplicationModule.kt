package cl.minkai.bloom.common.di

import android.content.Context
import android.content.SharedPreferences
import cl.minkai.bloom.common.factory.NetworkDependenciesFactory
import cl.minkai.mvi.execution.AppExecutionThread
import cl.minkai.mvi.execution.ExecutionThread
import cl.minkai.network.config.NetworkDependencies
import cl.minkai.network.security.PassportTokenManager
import cl.minkai.network.utils.NetworkErrorHandler
import cl.minkai.utils.factory.SharedPreferencesFactory
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule {
    @Provides
    fun providesExecutionThread(): ExecutionThread = AppExecutionThread()

    @Provides
    fun providesGeneralSharedPreferences(context: Context): SharedPreferences =
        SharedPreferencesFactory.makeGeneralSharedPreferences(context)

    @Provides
    fun providesPassportTokenManager(context: Context): PassportTokenManager =
        PassportTokenManager(providesGeneralSharedPreferences(context))

    @Provides
    fun providesNetWorkDependencies(
        context: Context,
        passportManager: PassportTokenManager
    ): NetworkDependencies =
        NetworkDependenciesFactory.makeNetworkDependencies(context, passportManager)

    @Provides
    fun providesNetworkErrorHandler(): NetworkErrorHandler = NetworkErrorHandler()
}