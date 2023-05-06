package ui.screen.home

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import data.DataRepository
import data.model.AccountInfo
import kotlinx.coroutines.launch

class HomeScreenModel(
    private val dataRepository: DataRepository
) : StateScreenModel<HomeScreenModel.State>(State.Loading) {

    sealed class State {
        object Loading : State()
        data class Result(val accountInfo: AccountInfo) : State()
    }

    init {
        getData()
    }

    fun getData() = coroutineScope.launch {
        mutableState.value =
            State.Result(
                accountInfo = dataRepository.getAccountInfo(-1).getOrNone().getOrNull()
                    ?: throw IllegalStateException("null")
            )
    }
}
