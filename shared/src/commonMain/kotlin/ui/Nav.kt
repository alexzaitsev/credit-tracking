package ui

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import ui.screen.AddTxScreen
import ui.screen.HomeScreen
import ui.screen.TxDetailsScreen

private val navigator: Navigator
    @Composable get() = LocalNavigator.currentOrThrow

internal object HomeDest : Screen {

    @Composable
    override fun Content() {
        val nav = navigator

        HomeScreen(
            onAddTxClicked = { nav push AddTxDest },
            onTxDetailsClicked = { txId -> nav push TxDetailsDest(txId) }
        )
    }
}

internal object AddTxDest : Screen {

    @Composable
    override fun Content() {
        val nav = navigator

        AddTxScreen(onBackPressed = nav::pop)
    }
}

internal data class TxDetailsDest(val txId: Int) : Screen {

    @Composable
    override fun Content() {
        val nav = navigator

        TxDetailsScreen(txId = txId, onBackPressed = nav::pop)
    }
}
