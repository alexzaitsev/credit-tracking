package ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.view.default.DefaultTopBar

@Composable
fun TxDetailsScreen(txId: Int, onBackPressed: () -> Unit) = Column {
    DefaultTopBar(
        title = "Details",
        onBackPressed = onBackPressed
    )
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Details of existing TX. ID: ${txId}")
    }
}
