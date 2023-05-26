package ui.screen.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.model.Account
import data.model.Tx
import ui.screen.observeState
import ui.util.print
import ui.util.printAmount
import ui.util.zeroBasedColor
import ui.view.default.DefaultButton
import ui.view.default.DefaultProgress
import ui.view.default.DefaultSpacer

@Composable
fun HomeScreen(
    sm: HomeScreenModel,
    onAddTxClicked: (String) -> Unit
) {
    when (val state = sm.observeState()) {
        HomeScreenModel.State.Loading -> DefaultProgress()
        is HomeScreenModel.State.Ready -> ReadyViewState(
            accounts = state.accounts,
            onAddTxClicked = onAddTxClicked
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ReadyViewState(
    accounts: List<Account>,
    onAddTxClicked: (String) -> Unit
) = Column(modifier = Modifier.padding(16.dp)) {

    // TODO general statuses here
    Text(
        modifier = Modifier.weight(0.3f),
        text = "status here"
    )

    val lazyListState = rememberLazyListState()
    LazyRow(
        modifier = Modifier.weight(0.7f),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        state = lazyListState,
        flingBehavior = rememberSnapFlingBehavior(lazyListState)
    ) {
        items(accounts) { account ->
            AccountItem(
                modifier = Modifier.width(300.dp).fillMaxHeight()
                    .background(
                        color = Color.Gray,
                        shape = RoundedCornerShape(5.dp)
                    ),
                account = account,
                onAddTxClicked = { onAddTxClicked(account.id) }
            )
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
