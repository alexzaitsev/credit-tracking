package ui.screen

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseScreenModel<State, SideEffect>(initialState: State) : ScreenModel {

    protected val _state: MutableStateFlow<State> = MutableStateFlow(initialState)
    val state: StateFlow<State> = _state.asStateFlow()

    protected val _sideEffects: MutableSharedFlow<SideEffect> = MutableSharedFlow()
    val sideEffects: SharedFlow<SideEffect> = _sideEffects.asSharedFlow()

    /**
     * Hides returned Job from the caller and adds convenient syntax
     */
    fun launch(block: suspend () -> Unit) {
        coroutineScope.launch {
            block()
        }
    }
}
