package ui.screen.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.screen.home.component.AccountItem
import ui.screen.home.component.AccountStatus
import ui.screen.observeState
import ui.util.lerp
import ui.view.default.DefaultProgress
import ui.view.default.DefaultSpacer
import kotlin.math.abs

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
        AccountStatus.Ok -> Color.Green
        is AccountStatus.Issue -> Color.Red
    }
    val text = when (status) {
        is AccountStatus.Issue -> status.message
        AccountStatus.Ok -> "EVERYTHING IS FINE"
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

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.weight(0.1f))
            AccountStatus(
                modifier = getGraphicsModifier(
                    pagerState = pagerState,
                    index = index,
                    startScale = 0.3f,
                    startAlpha = 0.1f
                ),
                status = status
            )
            Spacer(modifier = Modifier.weight(0.1f))

            val bg = when (status) {
                is AccountStatus.Issue -> Color.Red.copy(alpha = 0.6f)
                AccountStatus.Ok -> Color.LightGray
            }
            Card(
                shape = RoundedCornerShape(10.dp),
                elevation = 10.dp,
                modifier = Modifier
                    .weight(0.8f)
                    .then(
                        getGraphicsModifier(
                            pagerState = pagerState,
                            index = index,
                            startScale = 0.85f,
                            startAlpha = 0.5f
                        ),
                    )
            ) {
                AccountItem(
                    modifier = Modifier.fillMaxSize()
                        .background(color = bg)
                        .padding(16.dp),
                    account = account,
                    onAddTxClicked = { onAddTxClicked(account.id) }
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun getGraphicsModifier(pagerState: PagerState, index: Int, startScale: Float, startAlpha: Float) =
    Modifier.graphicsLayer {
        // |    0page 1p| = 0 for 0page, -1 for 1page
        // |age 1page 2p| = 1 for 0page, 0 for 1page, -1 for 2page
        val pageOffset =
            abs((pagerState.currentPage - index) + pagerState.currentPageOffsetFraction)

        val scale = lerp(
            start = startScale,
            stop = 1f,
            fraction = 1f - pageOffset.coerceIn(0f, 1f)
        )
        scaleX = scale
        scaleY = scale

        alpha = lerp(
            start = startAlpha,
            stop = 1f,
            fraction = 1f - pageOffset.coerceIn(0f, 1f)
        )
    }