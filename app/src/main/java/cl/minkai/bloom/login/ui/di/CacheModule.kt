package cl.minkai.bloom.login.ui.di

import cl.minkai.bloom.login.data.cache.LoginCacheImpl
import cl.minkai.bloom.login.data.source.LoginCache
import dagger.Binds
import dagger.Module

@Module
abstract class CacheModule {

    @Binds
    abstract fun bindCache(cache: LoginCacheImpl): LoginCache
}