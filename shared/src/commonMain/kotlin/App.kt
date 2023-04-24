import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.LocalDateTime
import kotlin.math.abs

@Composable
fun App() {
    MaterialTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                Name(modifier = Modifier.weight(1f), name = "Oksana")
                Name(modifier = Modifier.weight(1f), name = "Alex")
            }

            // mock data
            val cibcOksanaTxs = getMockCIBCOksanaTxs()
            val servusOksanaTxs = emptyList<Transaction>()
            val cibcAlexTxs = emptyList<Transaction>()
            val servusAlexTxs = emptyList<Transaction>()

            Row {
                Column(modifier = Modifier.weight(1f)) {
                    Account(
                        modifier = Modifier.weight(1f)
                            .background(color = Color.Gray, shape = RoundedCornerShape(5.dp)),
                        bankName = "CIBC",
                        transactions = cibcOksanaTxs
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Account(
                        modifier = Modifier.weight(1f).background(
                            color = Color.LightGray,
                            shape = RoundedCornerShape(5.dp)
                        ),
                        bankName = "Servus",
                        transactions = servusOksanaTxs
                    )
                }
                Spacer(modifier = Modifier.width(20.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Account(
                        modifier = Modifier.weight(1f).background(
                            color = Color.LightGray,
                            shape = RoundedCornerShape(5.dp)
                        ),
                        bankName = "CIBC",
                        transactions = cibcAlexTxs
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Account(
                        modifier = Modifier.weight(1f)
                            .background(color = Color.Gray, shape = RoundedCornerShape(5.dp)),
                        bankName = "Servus",
                        transactions = servusAlexTxs
                    )
                }
            }
        }
    }
}

@Composable
private fun Name(modifier: Modifier, name: String) = Text(
    modifier = modifier,
    textAlign = TextAlign.Center,
    fontWeight = FontWeight.Bold,
    fontSize = 22.sp,
    text = name
)

@Composable
private fun Account(modifier: Modifier, bankName: String, transactions: List<Transaction>) =
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            text = bankName
        )
        Status(transactions)
        Transactions(modifier = Modifier.weight(1f), transactions = transactions)
        AddBtn(modifier = Modifier.align(Alignment.CenterHorizontally))
    }

@Composable
private fun Transactions(modifier: Modifier, transactions: List<Transaction>) {
    LazyColumn(modifier = modifier) {
        items(transactions) { tx ->
            TransactionItem(tx)
        }
    }
}

@Composable
private fun TransactionItem(tx: Transaction) {
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
                text = tx.comment
            )
        }
    }
}

@Composable
private fun Status(transactions: List<Transaction>) = Column(
    modifier = Modifier.fillMaxWidth()
        .background(color = Color.Green, shape = RoundedCornerShape(5.dp))
        .padding(8.dp)
) {
    Text("No issues found")
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

expect fun getPlatformName(): String