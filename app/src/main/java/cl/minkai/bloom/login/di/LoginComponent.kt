package cl.minkai.bloom.login.di

import cl.minkai.bloom.common.di.ApplicationComponent
import cl.minkai.bloom.login.ui.LoginActivity
import cl.minkai.bloom.login.ui.login.LoginFragment
import cl.minkai.di.ActivityScope
import cl.minkai.network.config.NetworkDependencies
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ActivityScope
@Component(
    modules = [
        RemoteModule::class,
        CacheModule::class,
        FragmentModule::class
    ],
    dependencies = [ApplicationComponent::class]
)
interface LoginComponent {

    @FlowPreview
    @ExperimentalCoroutinesApi
    fun inject(fragment: LoginFragment)

    fun inject(activity: LoginActivity)
}