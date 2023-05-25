package ui

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.koin.core.component.KoinComponent
import org.koin.core.parameter.parametersOf
import ui.screen.addTx.AddTxScreen
import ui.screen.addTx.AddTxScreenModel
import ui.screen.home.HomeScreen
import ui.screen.home.HomeScreenModel

private val navigator: Navigator
    @Composable get() = LocalNavigator.currentOrThrow

internal object HomeDest : Screen, KoinComponent {

    @Composable
    override fun Content() {
        val nav = navigator
        val sm = getScreenModel<HomeScreenModel>()

        HomeScreen(
            sm = sm,
            onAddTxClicked = { accountId -> nav push AddTxDest(accountId) }
        )
    }
}

internal data class AddTxDest(
    val accountId: String
) : Screen, KoinComponent {

    @Composable
    override fun Content() {
        val nav = navigator
        val sm = getScreenModel<AddTxScreenModel> { parametersOf(accountId) }

        AddTxScreen(
            sm = sm,
            onBackClicked = nav::pop,
            onAdded = nav::pop
        )
    }
}
