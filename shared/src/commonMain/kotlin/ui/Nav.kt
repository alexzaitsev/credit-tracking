package ui

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import ui.screen.AddTxScreen
import ui.screen.HomeScreen
import ui.screen.TxDetailsScreen

object HomeDest : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        HomeScreen(
            onAddTxClicked = { navigator push AddTxDest },
            onTxDetailsClicked = { txId -> navigator push TxDetailsDest(txId) }
        )
    }
}

object AddTxDest : Screen {

    @Composable
    override fun Content() {
        AddTxScreen()
    }
}

data class TxDetailsDest(val txId: Int) : Screen {

    @Composable
    override fun Content() {
        TxDetailsScreen(txId = txId)
    }
}