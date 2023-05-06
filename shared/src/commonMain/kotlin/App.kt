
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import data.dataModule
import org.koin.core.context.startKoin
import ui.HomeDest
import ui.uiModule

@Composable
fun App() {
    initKoin()

    MaterialTheme {
        Navigator(
            screen = HomeDest
        )
    }
}

expect fun getPlatformName(): String

fun initKoin(){
    startKoin {
        modules(dataModule, uiModule)
    }
}
