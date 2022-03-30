package cl.minkai.mvi

import cl.minkai.mvi.events.MviUiState
import cl.minkai.mvi.events.MviUserIntent
import kotlinx.coroutines.flow.Flow

interface MviUi<TUserIntent : MviUserIntent, in TUiState : MviUiState> {
    fun userIntents(): Flow<TUserIntent>
    fun renderUiStates(uiStates: TUiState)
}