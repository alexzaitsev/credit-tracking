package ui.screen.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
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
import data.model.Account
import data.model.Tx
import ui.screen.observeState
import ui.util.issueBasedColor
import ui.util.lerp
import ui.util.print
import ui.util.printAmount
import ui.util.zeroBasedColor
import ui.view.default.DefaultButton
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

    Text(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = summary.numOfAccountsWithIssue.issueBasedColor)
            .padding(16.dp),
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
        text = "NUMBER OF ISSUES ${summary.numOfAccountsWithIssue}",
        color = Color.White
    )

    DefaultSpacer(16.dp)

    AccountsList(
        modifier = Modifier.weight(1f),
        accounts = summary.accounts,
        onAddTxClicked = onAddTxClicked
    )

    DefaultSpacer(16.dp)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AccountsList(
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

            when (status) {
                is AccountStatus.Issue -> Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = Color.Red,
                    text = status.message
                )

                AccountStatus.Ok -> {}
            }

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
                    .graphicsLayer {
                        // |    0page 1p| = 0 for 0page, -1 for 1page
                        // |age 1page 2p| = 1 for 0page, 0 for 1page, -1 for 2page
                        val pageOffset =
                            abs((pagerState.currentPage - index) + pagerState.currentPageOffsetFraction)

                        val scale = lerp(
                            start = 0.85f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                        scaleX = scale
                        scaleY = scale

                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    }
            ) {
                AccountItem(
                    modifier = Modifier.fillMaxSize()
                        .background(color = bg),
                    account = account,
                    onAddTxClicked = { onAddTxClicked(account.id) }
                )
            }
        }
    }
}

@Composable
private fun AccountItem(
    modifier: Modifier,
    account: Account,
    onAddTxClicked: () -> Unit
) = Column(modifier = modifier) {
    UserName(name = account.personName)
    DefaultSpacer(4.dp)

    BankName(name = account.bankName)
    DefaultSpacer(4.dp)

    Balance(balance = account.balance)
    DefaultSpacer(4.dp)

    Transactions(
        modifier = Modifier.weight(1f),
        txs = account.lastTxs
    )

    DefaultButton(
        modifier = Modifier.align(Alignment.CenterHorizontally),
        onClick = onAddTxClicked,
        text = "Add transaction"
    )
}

@Composable
private fun UserName(name: String) = Text(
    modifier = Modifier.fillMaxWidth(),
    textAlign = TextAlign.Center,
    fontWeight = FontWeight.Bold,
    fontSize = 22.sp,
    text = name
)

@Composable
private fun BankName(name: String) = Text(
    modifier = Modifier.fillMaxWidth(),
    textAlign = TextAlign.Center,
    fontWeight = FontWeight.SemiBold,
    fontSize = 20.sp,
    text = name
)

@Composable
private fun Balance(balance: Double) = Text(
    modifier = Modifier.fillMaxWidth(),
    textAlign = TextAlign.Center,
    fontWeight = FontWeight.SemiBold,
    fontSize = 20.sp,
    color = balance.zeroBasedColor,
    text = balance.printAmount()
)

@Composable
private fun Status(accountStatus: String) = Text(
    modifier = Modifier.fillMaxWidth()
        .background(color = Color.Green, shape = RoundedCornerShape(5.dp))
        .padding(8.dp),
    textAlign = TextAlign.Center,
    text = accountStatus
)

@Composable
private fun Transactions(modifier: Modifier, txs: List<Tx>) {
    LazyColumn(modifier = modifier) {
        items(txs) { tx ->
            TxItem(tx = tx)
        }
    }
}

@Composable
private fun TxItem(tx: Tx) {
    Column(modifier = Modifier.padding(4.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                modifier = Modifier.padding(end = 8.dp),
                fontSize = 12.sp,
                text = tx.dateTime.print(twoLines = true)
            )
            Text(
                modifier = Modifier.padding(end = 5.dp),
                color = tx.amount.zeroBasedColor,
                text = tx.amount.printAmount()
            )
        }
        if (tx.comment.isNotEmpty()) {
            Text(
                modifier = Modifier.padding(top = 2.dp),
                fontSize = 12.sp,
                color = Color(0xff3d3d3d),
                text = tx.comment
            )
        }
    }
}
