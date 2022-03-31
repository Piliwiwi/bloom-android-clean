package cl.minkai.bloom

import android.app.Application
import cl.minkai.bloom.common.di.ApplicationComponent
import cl.minkai.bloom.common.di.DaggerApplicationComponent

class BloomApplication : Application() {
    val appComponent: ApplicationComponent by lazy {
        initializeComponent()
    }

    private fun initializeComponent(): ApplicationComponent = DaggerApplicationComponent
        .factory()
        .create(
            application = this,
            context = applicationContext
        )
}