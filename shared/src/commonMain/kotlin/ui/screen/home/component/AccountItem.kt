package ui.screen.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.model.Account
import data.model.Tx
import ui.screen.home.AccountStatus
import ui.util.print
import ui.util.printAmount
import ui.util.zeroBasedColor
import ui.view.default.DefaultSpacer

@Composable
fun AccountStatus(modifier: Modifier, status: AccountStatus) = when (status) {
    is AccountStatus.Issue -> Text(
        modifier = Modifier.fillMaxWidth().then(modifier),
        textAlign = TextAlign.Center,
        color = Color.Red,
        text = status.message,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp
    )

    AccountStatus.Ok -> Icon(
        modifier = Modifier.size(100.dp).then(modifier),
        painter = rememberVectorPainter(Icons.Filled.Done),
        tint = Color.Green,
        contentDescription = null
    )
}

@Composable
fun AccountItem(
    modifier: Modifier,
    account: Account,
    onAddTxClicked: () -> Unit
) = Column(modifier = modifier) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        UserName(name = account.personName)
        DefaultSpacer(8.dp)
        BankName(name = account.bankName)
    }
    Balance(balance = account.balance)
    DefaultSpacer(16.dp)

    Transactions(
        modifier = Modifier.fillMaxWidth().weight(1f),
        txs = account.lastTxs,
        onAddTxClicked = onAddTxClicked
    )
}

@Composable
private fun UserName(name: String) = Text(
    textAlign = TextAlign.Center,
    fontWeight = FontWeight.Bold,
    fontSize = 22.sp,
    text = name
)

@Composable
private fun BankName(name: String) = Text(
    textAlign = TextAlign.Center,
    fontWeight = FontWeight.SemiBold,
    fontSize = 22.sp,
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
private fun Transactions(
    modifier: Modifier,
    txs: List<Tx>,
    onAddTxClicked: () -> Unit
) = Box(modifier = modifier) {
    LazyColumn {
        items(txs) { tx ->
            Column {
                TxItem(tx = tx)
                DefaultSpacer(4.dp)
            }
        }
        if (txs.isNotEmpty()) {
            item {
                Text(
                    modifier = Modifier.padding(8.dp),
                    color = Color.Gray,
                    fontSize = 12.sp,
                    fontStyle = FontStyle.Italic,
                    text = "*Last 5 transactions"
                )
            }
        }
    }
    FloatingActionButton(
        modifier = Modifier.align(Alignment.BottomEnd),
        shape = CircleShape,
        onClick = onAddTxClicked,
    ) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = null)
    }
}

@Composable
private fun TxItem(tx: Tx) {
    Card(
        backgroundColor = Color.LightGray,
        shape = RoundedCornerShape(10.dp),
        elevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(end = 8.dp),
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                text = tx.dateTime.print(twoLines = true)
            )
            if (tx.comment.isNotEmpty()) {
                Text(
                    modifier = Modifier.weight(1f).padding(end = 8.dp),
                    fontSize = 12.sp,
                    color = Color(0xff3d3d3d),
                    text = tx.comment
                )
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }
            Text(
                fontWeight = FontWeight.Bold,
                color = tx.amount.zeroBasedColor,
                text = tx.amount.printAmount()
            )
        }
    }
}
