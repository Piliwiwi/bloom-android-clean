package cl.minkai.bloom

import android.app.Application
import cl.minkai.bloom.common.di.ApplicationComponent
import cl.minkai.bloom.common.di.DaggerApplicationComponent
import cl.minkai.bloom.common.featureflags.FeatureFlags
import cl.minkai.bloom.common.featureflags.FirebaseRemoteConfig

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

    override fun onCreate() {
        super.onCreate()
        FirebaseRemoteConfig.initRemoteConfig(
            FeatureFlags.getDefaults(),
            BuildConfig.DEBUG
        )
    }
}