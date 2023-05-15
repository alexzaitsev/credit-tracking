package ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.model.Account
import data.model.Tx
import ui.util.print
import ui.util.printAmount
import ui.util.zeroBasedColor
import ui.view.default.DefaultButton

@Composable
fun HomeScreen(
    sm: HomeScreenModel,
    onAddTxClicked: () -> Unit
) {
    val state by sm.state.collectAsState()
    when (state) {
        HomeScreenModel.State.Loading -> CircularProgressIndicator()
        is HomeScreenModel.State.Result -> ReadyViewState(
            accounts = (state as HomeScreenModel.State.Result).accounts,
            onAddTxClicked = onAddTxClicked
        )
    }
}

@Composable
private fun ReadyViewState(
    accounts: List<Account>,
    onAddTxClicked: () -> Unit
) = Column(modifier = Modifier.padding(16.dp)) {

    // TODO general statuses here
    Text("data below")

    LazyRow {
        items(accounts) { account ->
            AccountItem(
                modifier = Modifier.width(200.dp).fillMaxHeight()
                    .background(color = Color.Gray, shape = RoundedCornerShape(5.dp)),
                account = account,
                onAddTxClicked = onAddTxClicked
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
    BankNameAndBalance(bankName = account.bankName, balance = account.balance)
    Status(accountStatus = "No issues")
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
private fun UserName(modifier: Modifier = Modifier, name: String) = Text(
    modifier = modifier,
    textAlign = TextAlign.Center,
    fontWeight = FontWeight.Bold,
    fontSize = 22.sp,
    text = name
)

@Composable
private fun BankNameAndBalance(bankName: String, balance: Double) =
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            text = bankName
        )
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            color = balance.zeroBasedColor,
            text = "$balance"
        )
    }

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
