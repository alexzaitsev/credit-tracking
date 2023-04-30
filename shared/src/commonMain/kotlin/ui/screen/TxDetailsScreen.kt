package ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.getMockCIBCOksanaTxs
import ui.util.print
import ui.util.printAmount
import ui.util.zeroBasedColor
import ui.view.default.DefaultSpacer
import ui.view.default.DefaultTopBar

@Composable
fun TxDetailsScreen(txId: Int, onBackPressed: () -> Unit) = Column {
    DefaultTopBar(
        title = "Details for tx #$txId",
        onBackPressed = onBackPressed
    )

    val tx = getMockCIBCOksanaTxs().firstOrNull { it.id == txId }
    if (tx != null) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = tx.dateTime.print()
            )
            DefaultSpacer(8.dp)
            Text(
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                color = tx.amount.zeroBasedColor,
                text = tx.amount.printAmount()
            )
            DefaultSpacer(8.dp)
            if (tx.comment.isNotEmpty()) {
                Text(
                    fontSize = 12.sp,
                    color = Color.DarkGray,
                    text = "Comment:"
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = tx.comment
                )
            }
        }
    }
}
