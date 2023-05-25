package ui.screen.addTx

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import data.DataRepository
import data.cloud.now
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

    fun addTx(amount: Double, comment: String) = coroutineScope.launch {
        mutableState.value = State.Loading
        dataRepository.addTx(
            tx = Tx(
                dateTime = now(),
                amount = amount,
                comment = comment
            ),
            accountId = accountId
        )
        mutableState.value = State.Added
    }
}
