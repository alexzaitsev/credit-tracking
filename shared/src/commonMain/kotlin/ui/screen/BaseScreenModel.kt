package ui.screen

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.launch

abstract class BaseScreenModel : ScreenModel {

    /**
     * Hides returned Job from the caller and adds convenient syntax
     */
    fun launch(block: suspend () -> Unit) {
        coroutineScope.launch {
            block()
        }
    }
}
