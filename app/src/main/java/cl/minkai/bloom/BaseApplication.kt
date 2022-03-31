package cl.minkai.bloom

import android.app.Application

abstract class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        onInjectApplication()
        onPrepareApplication()
    }

    protected abstract fun onPrepareApplication()
    protected abstract fun onInjectApplication()
}