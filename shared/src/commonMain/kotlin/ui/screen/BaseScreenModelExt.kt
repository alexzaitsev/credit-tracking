package ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import common.util.OneTime
import kotlinx.coroutines.flow.collectLatest

@Composable
fun <State : Any, SideEffect> BaseScreenModel<State, SideEffect>.observeState(): State {
    val state by state.collectAsState()
    return state
}

@Composable
fun <State : Any, SideEffect> BaseScreenModel<State, SideEffect>.observeSideEffect(collector: (SideEffect) -> Unit) {
    OneTime {
        sideEffects.collectLatest(collector)
    }
}
