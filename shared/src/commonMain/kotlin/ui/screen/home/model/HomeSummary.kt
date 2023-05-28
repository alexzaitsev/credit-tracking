package ui.screen.home.model

data class HomeSummary(
    val accounts: List<AccountExtended>,
    val generalStatus: AccountStatus
)
