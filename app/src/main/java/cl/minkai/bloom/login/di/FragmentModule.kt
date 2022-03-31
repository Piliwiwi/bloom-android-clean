package cl.minkai.bloom.login.di

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import dagger.Module
import dagger.Provides

@Module
class FragmentModule(
    val fragmentManager: FragmentManager,
    val lifecycle: Lifecycle
) {
    @Provides
    fun providesFragmentManager(): FragmentManager = fragmentManager

    @Provides
    fun providesLifecycle(): Lifecycle = lifecycle
}