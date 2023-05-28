package ui.screen.home.model

sealed class AccountStatus {
    object Ok : AccountStatus()
    data class Issue(val message: String) : AccountStatus()
}
