package cl.minkai.mvi.execution

import cl.minkai.mvi.execution.ExecutionThreadEnvironment.APPLICATION
import cl.minkai.mvi.execution.ExecutionThreadEnvironment.TESTING

object ExecutionThreadFactory {
    fun makeExecutionThread(environment: ExecutionThreadEnvironment): ExecutionThread =
        when (environment) {
            APPLICATION -> AppExecutionThread()
            TESTING -> AppExecutionThread()
        }
}