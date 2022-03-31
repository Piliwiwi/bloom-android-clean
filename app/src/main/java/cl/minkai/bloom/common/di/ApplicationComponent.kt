package cl.minkai.bloom.common.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import cl.minkai.bloom.BloomApplication
import cl.minkai.mvi.execution.ExecutionThread
import cl.minkai.network.config.NetworkDependencies
import cl.minkai.network.security.PassportTokenManager
import cl.minkai.network.utils.NetworkErrorHandler
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModule::class
    ]
)
interface ApplicationComponent {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application,
            @BindsInstance context: Context
        ): ApplicationComponent
    }

    fun getExecutionThread(): ExecutionThread
    fun getGeneralSharedPreferences(): SharedPreferences
    fun getPassportTokenManager(): PassportTokenManager
    fun getNetWorkDependencies(): NetworkDependencies
    fun getErrorHandler(): NetworkErrorHandler

    fun inject(app: BloomApplication)
}