package ui

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import ui.screen.AddTxScreen
import ui.screen.home.HomeScreen
import ui.screen.home.HomeScreenModel

private val navigator: Navigator
    @Composable get() = LocalNavigator.currentOrThrow

internal object HomeDest : Screen {

    @Composable
    override fun Content() {
        val nav = navigator
        val sm = rememberScreenModel { HomeScreenModel() }

        HomeScreen(
            sm = sm,
            onAddTxClicked = { nav push AddTxDest }
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
