package ui.screen.home

import data.DataRepository
import data.model.Account
import kotlinx.coroutines.flow.collectLatest
import ui.screen.BaseScreenModel

class HomeScreenModel(
    private val dataRepository: DataRepository
) : BaseScreenModel<HomeScreenModel.State, HomeScreenModel.SideEffect>(State.Loading) {

    sealed class State {
        object Loading : State()
        data class Ready(val accounts: List<Account>) : State()
    }

    sealed class SideEffect {
    }

    init {
        observeData()
    }

    private fun observeData() = launch {
        dataRepository.observeAccounts().collectLatest { accounts: List<Account> ->
            _state.value = State.Ready(accounts)
        }
    }
}
