package ui.screen.addTx

import cafe.adriel.voyager.core.model.coroutineScope
import data.DataRepository
import data.source.cloud.now
import data.model.Tx
import kotlinx.coroutines.launch
import ui.screen.BaseScreenModel

class AddTxScreenModel(
    private val accountId: String,
    private val dataRepository: DataRepository
) : BaseScreenModel<AddTxScreenModel.State, AddTxScreenModel.SideEffect>(State.Initial) {

    sealed class State {
        object Initial : State()
        object Loading : State()
    }

    sealed class SideEffect {
        object TxAdded : SideEffect()
    }

    fun addTx(amount: Double, comment: String) = coroutineScope.launch {
        _state.value = State.Loading
        dataRepository.addTx(
            tx = Tx(
                dateTime = now(),
                amount = amount,
                comment = comment
            ),
            accountId = accountId
        )
        _sideEffects.emit(SideEffect.TxAdded)
    }
}
