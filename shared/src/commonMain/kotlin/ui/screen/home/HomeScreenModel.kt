package ui.screen.home

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import data.DataRepository
import data.model.Account
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeScreenModel(
    private val dataRepository: DataRepository
) : StateScreenModel<HomeScreenModel.State>(State.Loading) {

    sealed class State {
        object Loading : State()
        data class Result(val accounts: List<Account>) : State()
    }

    init {
        observeData()
    }

    private fun observeData() = coroutineScope.launch {
        dataRepository.observeAccounts().collectLatest { accounts: List<Account> ->
            mutableState.value = State.Result(accounts)
        }
    }
}
