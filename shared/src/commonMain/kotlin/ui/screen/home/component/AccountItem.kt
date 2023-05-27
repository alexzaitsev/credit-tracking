package ui.screen.home.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.PagerState
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.model.Account
import data.model.Tx
import ui.screen.home.AccountStatus
import ui.util.lerp
import ui.util.print
import ui.util.printAmount
import ui.util.zeroBasedColor
import ui.view.default.DefaultSpacer
import ui.view.theme.Colors
import kotlin.math.abs

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AccountPage(
    account: Account,
    status: AccountStatus,
    index: Int,
    pagerState: PagerState,
    onAddTxClicked: (String) -> Unit
) = Column(horizontalAlignment = Alignment.CenterHorizontally) {
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
        is AccountStatus.Issue -> Colors.AMBER_300
        AccountStatus.Ok -> Colors.DEEP_PURPLE_300
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

@Composable
fun AccountStatus(modifier: Modifier, status: AccountStatus) = when (status) {
    is AccountStatus.Issue -> Text(
        modifier = Modifier.fillMaxWidth().then(modifier),
        textAlign = TextAlign.Center,
        color = Colors.PINK_600,
        text = status.message,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp
    )

    AccountStatus.Ok -> Icon(
        modifier = Modifier.width(100.dp).then(modifier),
        painter = rememberVectorPainter(Icons.Filled.Done),
        tint = Colors.GREEN_600,
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
                    color = Colors.AMBER_200,
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
        backgroundColor = Colors.BLUE_300,
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
                color = Colors.WHITE,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                text = tx.dateTime.print(twoLines = true)
            )
            if (tx.comment.isNotEmpty()) {
                Text(
                    modifier = Modifier.weight(1f).padding(end = 8.dp),
                    fontSize = 12.sp,
                    color = Colors.GRAY_200,
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