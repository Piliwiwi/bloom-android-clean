package cl.minkai.mvi

import cl.minkai.mvi.events.MviResult
import cl.minkai.mvi.events.MviUiState

interface MviReducer<TUiState : MviUiState, TResult : MviResult> {
    infix fun TUiState.reduceWith(result: TResult): TUiState
}