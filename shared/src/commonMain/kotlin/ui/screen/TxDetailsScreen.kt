package ui.screen

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun TxDetailsScreen(txId: Int) {
    Text("Details of existing TX. ID: ${txId}")
}
