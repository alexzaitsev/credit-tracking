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
        data class Ready(val summary: HomeSummary) : State()
    }

    sealed class SideEffect {
    }

    init {
        observeData()
    }

    private fun observeData() = launch {
        dataRepository.observeAccounts().collectLatest { accounts: List<Account> ->
            val accountExtended = accounts.mapIndexed { index, account ->
                // TODO stub data
                val status = if (index == 0 || index == 1) AccountStatus.Issue("2 weeks no usage") else AccountStatus.Ok
                AccountExtended(
                    account = account,
                    status = status
                )
            }
            val numOfAccountsWithIssue = accountExtended.count { it.status is AccountStatus.Issue }

            _state.value = State.Ready(
                HomeSummary(
                    accounts = accountExtended,
                    numOfAccountsWithIssue = numOfAccountsWithIssue
                )
            )
        }
    }
}

sealed class AccountStatus {
    object Ok : AccountStatus()
    data class Issue(val message: String) : AccountStatus()
}

data class AccountExtended(
    val account: Account,
    val status: AccountStatus
)

data class HomeSummary(
    val accounts: List<AccountExtended>,
    val numOfAccountsWithIssue: Int
)
