
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import ui.HomeDest

@Composable
fun App() {
    MaterialTheme {
        Navigator(
            screen = HomeDest
        )
    }
}

expect fun getPlatformName(): String