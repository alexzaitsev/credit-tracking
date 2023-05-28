package ui.screen.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.screen.home.component.AccountPage
import ui.screen.home.component.colors
import ui.screen.home.model.AccountExtended
import ui.screen.home.model.AccountStatus
import ui.screen.home.model.HomeSummary
import ui.screen.observeState
import ui.view.default.DefaultProgress
import ui.view.default.DefaultSpacer
import ui.view.default.DefaultTopBarNoBack
import ui.view.theme.Strings

@Composable
fun HomeScreen(
    sm: HomeScreenModel,
    onAddTxClicked: (String) -> Unit
) {
    when (val state = sm.observeState()) {
        HomeScreenModel.State.Loading -> DefaultProgress()
        is HomeScreenModel.State.Ready -> ReadyViewState(
            summary = state.summary,
            onAddTxClicked = onAddTxClicked
        )
    }
}

@Composable
private fun ReadyViewState(
    summary: HomeSummary,
    onAddTxClicked: (String) -> Unit
) = Column {
    GeneralStatus(status = summary.generalStatus)
    DefaultSpacer(16.dp)

    AccountsList(
        modifier = Modifier.weight(1f),
        accounts = summary.accounts,
        onAddTxClicked = onAddTxClicked
    )
    DefaultSpacer(16.dp)
}

@Composable
private fun GeneralStatus(status: AccountStatus) {
    val text = when (status) {
        is AccountStatus.Issue -> status.message
        AccountStatus.Ok -> Strings.EVERYTHING_IS_FINE
    }

    DefaultTopBarNoBack(
        title = text,
        backgroundColor = status.colors.generalStatusBg,
        textColor = status.colors.generalStatusText
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun AccountsList(
    modifier: Modifier,
    accounts: List<AccountExtended>,
    onAddTxClicked: (String) -> Unit
) {
    val pagerState = rememberPagerState(initialPage = 0)

    HorizontalPager(
        modifier = modifier,
        pageCount = accounts.size, state = pagerState,
        pageSpacing = (-15).dp,
        contentPadding = PaddingValues(horizontal = 40.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) { index ->
        AccountPage(
            account = accounts[index],
            index = index,
            pagerState = pagerState,
            onAddTxClicked = onAddTxClicked
        )
    }
}
