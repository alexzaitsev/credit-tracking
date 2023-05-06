package ui

import androidx.compose.runtime.Composable

import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import ui.screen.AddTxScreen
import ui.screen.home.HomeScreen
import ui.screen.home.HomeScreenModel

private val navigator: Navigator
    @Composable get() = LocalNavigator.currentOrThrow

internal object HomeDest : Screen, KoinComponent {

    @Composable
    override fun Content() {
        val nav = navigator
        val sm = get<HomeScreenModel>()

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
