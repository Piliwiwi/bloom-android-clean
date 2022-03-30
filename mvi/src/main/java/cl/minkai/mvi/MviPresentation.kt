package cl.minkai.mvi

import cl.minkai.mvi.events.MviUiState
import cl.minkai.mvi.events.MviUserIntent
import kotlinx.coroutines.flow.Flow

interface MviPresentation<TUserIntent : MviUserIntent, TUiState : MviUiState> {
    fun processUserIntentsAndObserveUiStates(userIntents: Flow<TUserIntent>): Flow<TUiState>
}