package cl.minkai.bloom.common.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import cl.minkai.bloom.BloomApplication
import cl.minkai.mvi.execution.ExecutionThread
import cl.minkai.network.security.PassportTokenManager
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModule::class
    ]
)
interface ApplicationComponent {
    fun getApplication(): Application
    fun getContext(): Context
    fun getExecutionThread(): ExecutionThread
    fun getGeneralSharedPreferences(): SharedPreferences
    fun getPassportTokenManager(): PassportTokenManager

    fun inject(app: BloomApplication)
}