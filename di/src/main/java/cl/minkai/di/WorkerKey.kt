package cl.minkai.di

import androidx.work.Worker
import kotlin.reflect.KClass

annotation class WorkerKey(val value: KClass<out Worker>)