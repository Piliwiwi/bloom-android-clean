package cl.minkai.bloom.login.ui.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cl.minkai.bloom.login.presentation.LoginViewModel
import cl.minkai.di.ViewModelFactory
import cl.minkai.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@Module
abstract class PresentationModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @FlowPreview
    @ExperimentalCoroutinesApi
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel
}