package ui.screen.addTx

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import data.DataRepository
import data.model.Tx
import kotlinx.coroutines.launch

class AddTxScreenModel(
    private val accountId: String,
    private val dataRepository: DataRepository
) : StateScreenModel<AddTxScreenModel.State>(State.Initial) {

    sealed class State {
        object Initial : State()
        object Loading : State()
        object Added : State()
    }

    private fun addTx(tx: Tx) = coroutineScope.launch {
        mutableState.value = State.Loading
        dataRepository.addTx(tx = tx, accountId = accountId)
    }
}
