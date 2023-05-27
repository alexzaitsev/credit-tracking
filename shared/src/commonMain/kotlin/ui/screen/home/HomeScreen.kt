package ui.screen.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.screen.home.component.AccountPage
import ui.screen.observeState
import ui.view.default.DefaultProgress
import ui.view.default.DefaultSpacer
import ui.view.theme.Colors

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
    GeneralStatus(summary.generalStatus)
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
    val color = when (status) {
        AccountStatus.Ok -> Colors.GREEN_600
        is AccountStatus.Issue -> Colors.PINK_600
    }
    val text = when (status) {
        is AccountStatus.Issue -> status.message
        AccountStatus.Ok -> STRING_EVERYTHING_IS_FINE
    }

    Surface(elevation = 10.dp) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = color)
                .padding(16.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            text = text,
            color = Color.White
        )
    }
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
        val (account, status) = accounts[index]

        AccountPage(
            account = account,
            status = status,
            index = index,
            pagerState = pagerState,
            onAddTxClicked = onAddTxClicked
        )
    }
}
