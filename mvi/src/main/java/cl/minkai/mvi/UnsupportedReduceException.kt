package cl.minkai.mvi

import cl.minkai.mvi.events.MviResult
import cl.minkai.mvi.events.MviUiState
import java.lang.RuntimeException

class UnsupportedReduceException(state: MviUiState, result: MviResult) :
    RuntimeException("Cannot reduce state: $state with result: $result")