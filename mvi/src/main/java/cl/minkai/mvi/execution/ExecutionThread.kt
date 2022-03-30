package cl.minkai.mvi.execution

import kotlinx.coroutines.CoroutineDispatcher

interface ExecutionThread {
    fun uiThread(): CoroutineDispatcher
    fun ioThread(): CoroutineDispatcher
}