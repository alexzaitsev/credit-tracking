package ui.screen.home

import data.DataRepository
import data.model.Account
import data.source.cloud.TIME_ZONE
import kotlinx.coroutines.flow.collectLatest
import kotlinx.datetime.Clock
import kotlinx.datetime.toInstant
import ui.screen.BaseScreenModel
import ui.screen.home.model.AccountExtended
import ui.screen.home.model.AccountStatus
import ui.screen.home.model.HomeSummary
import kotlin.math.abs

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
            val accountExtended = getExtendedAccounts(accounts)
            val numOfAccountsWithIssue = accountExtended.count { it.status is AccountStatus.Issue }

            _state.value = State.Ready(
                HomeSummary(
                    accounts = accountExtended,
                    generalStatus = getGeneralStatus(numOfAccountsWithIssue)
                )
            )
        }
    }
}

const val STRING_EVERYTHING_IS_FINE = "EVERYTHING IS FINE"
const val STRING_LAST_TXS = "*Last 5 transactions"
private const val STRING_1_ACC_NEEDS_ATT = "1 ACCOUNT NEEDS ATTENTION"
private const val STRING_N_ACCS_NEED_ATT = "ACCOUNTS NEED ATTENTION"
private const val STRING_2_WEEKS_NO_USAGE = "2 weeks no usage"
private const val STRING_BALANCE_IS_LOW = "Balance is below 30%"

private fun getExtendedAccounts(accounts: List<Account>): List<AccountExtended> =
    accounts.map { account ->
        AccountExtended(
            account = account,
            status = getAccountStatus(account)
        )
    }.sortedWith { l, r ->
        if (l.status is AccountStatus.Issue && r.status is AccountStatus.Ok) {
            -1
        } else if (l.status is AccountStatus.Ok && r.status is AccountStatus.Issue) {
            1
        } else {
            val personComp = l.account.personName.compareTo(r.account.personName)
            if (personComp == 0) {
                l.account.bankName.compareTo(r.account.bankName)
            } else {
                personComp
            }
        }
    }

private fun getAccountStatus(account: Account): AccountStatus {
    if (account.balance < -75) {
        return AccountStatus.Issue(STRING_BALANCE_IS_LOW) // TODO TEMP
    }

    if (account.balance < 0 && abs(account.balance) > getLimit(account.bankName) * 0.3f) {
        return AccountStatus.Issue(STRING_BALANCE_IS_LOW)
    }

    account.lastTxs.maxByOrNull { it.dateTime }?.let { lastTx ->
        val lastTxTimestamp = lastTx.dateTime.toInstant(TIME_ZONE)
        if ((Clock.System.now() - lastTxTimestamp).inWholeDays >= 14) {
            return AccountStatus.Issue(STRING_2_WEEKS_NO_USAGE)
        }
    }

    return AccountStatus.Ok
}

private fun getGeneralStatus(numOfAccountsWithIssue: Int) = when (numOfAccountsWithIssue) {
    0 -> AccountStatus.Ok
    1 -> AccountStatus.Issue(message = STRING_1_ACC_NEEDS_ATT)
    else -> AccountStatus.Issue(message = "$numOfAccountsWithIssue $STRING_N_ACCS_NEED_ATT")
}

private fun getLimit(bankName: String): Int = when (bankName.lowercase()) {
    "cibc" -> 1000
    "servus" -> 1500
    else -> 0
}
