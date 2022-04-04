package cl.minkai.bloom

import android.app.Application
import cl.minkai.bloom.common.di.ApplicationComponent
import cl.minkai.bloom.common.di.DaggerApplicationComponent
import cl.minkai.featureflags.FeatureFlags
import cl.minkai.featureflags.FirebaseRemoteConfig

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
        cl.minkai.featureflags.FirebaseRemoteConfig.initRemoteConfig(
            cl.minkai.featureflags.FeatureFlags.getDefaults(),
            BuildConfig.DEBUG
        )
    }
}