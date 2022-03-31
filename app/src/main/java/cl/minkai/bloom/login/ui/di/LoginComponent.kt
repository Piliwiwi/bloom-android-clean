package cl.minkai.bloom.login.ui.di

import cl.minkai.bloom.common.di.ApplicationComponent
import cl.minkai.bloom.login.ui.LoginActivity
import cl.minkai.bloom.login.ui.login.LoginFragment
import cl.minkai.di.ActivityScope
import dagger.Component
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ActivityScope
@Component(
    modules = [
        RemoteModule::class,
        CacheModule::class,
        PresentationModule::class,
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