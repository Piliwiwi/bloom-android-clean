package cl.minkai.bloom.common.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import cl.minkai.mvi.execution.AppExecutionThread
import cl.minkai.mvi.execution.ExecutionThread
import cl.minkai.network.security.PassportTokenManager
import cl.minkai.utils.factory.SharedPreferencesFactory
import dagger.Module
import dagger.Provides

@Module
abstract class ApplicationModule {

    @Module
    companion object {
        private var application: Application? = null

        fun setApplication(app: Application) {
            application = app
        }

        @Provides
        @JvmStatic
        fun providesApplication(): Application = application!!


        @Provides
        @JvmStatic
        fun providesContext(): Context = application!!

        @Provides
        @JvmStatic
        fun providesExecutionThread(): ExecutionThread = AppExecutionThread()

        @Provides
        @JvmStatic
        fun providesGeneralSharedPreferences(): SharedPreferences =
            SharedPreferencesFactory.makeGeneralSharedPreferences(
                application!!
            )

        @Provides
        @JvmStatic
        fun providesPassportTokenManager(): PassportTokenManager =
            PassportTokenManager(providesGeneralSharedPreferences())
    }
}