package ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.getMockCIBCOksanaTxs
import data.model.AccountInfo
import data.model.Transaction
import kotlinx.datetime.LocalDateTime
import kotlin.math.abs

@Composable
fun HomeScreen() {
    Column(modifier = Modifier.padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)) {
            UserName(modifier = Modifier.weight(1f), name = "Oksana")
            UserName(modifier = Modifier.weight(1f), name = "Alex")
        }

        Row {
            Column(modifier = Modifier.weight(1f)) {
                AccountInfo(
                    modifier = Modifier.weight(1f)
                        .background(color = Color.Gray, shape = RoundedCornerShape(5.dp)),
                    accountInfo = AccountInfo(
                        bankName = "CIBC",
                        balance = 200.0f,
                        status = "No issues found",
                        lastTxs = getMockCIBCOksanaTxs()
                    )
                )
                Spacer(modifier = Modifier.height(20.dp))
                AccountInfo(
                    modifier = Modifier.weight(1f).background(
                        color = Color.LightGray,
                        shape = RoundedCornerShape(5.dp)
                    ),
                    accountInfo = AccountInfo(
                        bankName = "Servus",
                        balance = -100.0f,
                        status = "No issues found",
                        lastTxs = emptyList<Transaction>()
                    )
                )
            }
            Spacer(modifier = Modifier.width(20.dp))
            Column(modifier = Modifier.weight(1f)) {
                AccountInfo(
                    modifier = Modifier.weight(1f).background(
                        color = Color.LightGray,
                        shape = RoundedCornerShape(5.dp)
                    ),
                    accountInfo = AccountInfo(
                        bankName = "CIBC",
                        balance = -57.0f,
                        status = "No issues found",
                        lastTxs = emptyList<Transaction>()
                    )
                )
                Spacer(modifier = Modifier.height(20.dp))
                AccountInfo(
                    modifier = Modifier.weight(1f)
                        .background(color = Color.Gray, shape = RoundedCornerShape(5.dp)),
                    accountInfo = AccountInfo(
                        bankName = "Servus",
                        balance = 63.0f,
                        status = "No issues found",
                        lastTxs = emptyList<Transaction>()
                    )
                )
            }
        }
    }
}


@Composable
private fun UserName(modifier: Modifier, name: String) = Text(
    modifier = modifier,
    textAlign = TextAlign.Center,
    fontWeight = FontWeight.Bold,
    fontSize = 22.sp,
    text = name
)

@Composable
private fun AccountInfo(
    modifier: Modifier,
    accountInfo: AccountInfo
) = Column(modifier = modifier) {
    BankNameAndBalance(bankName = accountInfo.bankName, balance = accountInfo.balance)
    Status(accountStatus = accountInfo.status)
    Transactions(modifier = Modifier.weight(1f), txs = accountInfo.lastTxs)
    AddBtn(modifier = Modifier.align(Alignment.CenterHorizontally))
}

@Composable
private fun BankNameAndBalance(bankName: String, balance: Float) =
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
            color = if (balance < 0) Color.Red else Color.Green,
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
private fun Transactions(modifier: Modifier, txs: List<Transaction>) {
    LazyColumn(modifier = modifier) {
        items(txs) { tx ->
            TxItem(tx)
        }
    }
}

@Composable
private fun TxItem(tx: Transaction) {
    Column(modifier = Modifier.padding(4.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                modifier = Modifier.padding(end = 8.dp),
                fontSize = 12.sp,
                text = tx.dateTime.print()
            )
            Text(
                modifier = Modifier.padding(end = 5.dp),
                color = if (tx.amount < 0) Color.Red else Color.Green,
                text = if (tx.amount < 0) "-$${abs(tx.amount)}" else "$${tx.amount}"
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

@Composable
private fun AddBtn(modifier: Modifier) = Button(
    modifier = modifier,
    onClick = {
        // TODO navigate
    },
    content = {
        Text("Add transaction")
    })

private fun LocalDateTime.print() =
    "$year-${monthNumber.applyZero()}-${dayOfMonth.applyZero()}\n${hour.applyZero()}:${minute.applyZero()}"

private fun Int.applyZero() = if (this < 10) "0$this" else "$this"
