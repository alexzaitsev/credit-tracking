package common.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import ui.view.rememberMutable

@Composable
fun OneTime(block: suspend CoroutineScope.() -> Unit) = LaunchedEffect(Unit, block)

@Composable
fun OneTimeUi(block: () -> Unit) {
    var fired by rememberMutable(false)
    if (!fired) {
        block()
        fired = true
    }
}
