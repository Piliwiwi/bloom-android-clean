package cl.minkai.bloom

import android.app.Application
import cl.minkai.bloom.common.di.ApplicationComponent
import cl.minkai.bloom.common.di.DaggerApplicationComponent

class BloomApplication : BaseApplication() {
    private var applicationComponent: ApplicationComponent? = null

    override fun onPrepareApplication() {
        TODO("Not yet implemented")
    }

    override fun onInjectApplication() {
//        applicationComponent = DaggerApplicationComponent.builder().
    }


}