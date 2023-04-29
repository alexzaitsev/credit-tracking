
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import ui.screen.HomeScreen

@Composable
fun App() {
    MaterialTheme {
        HomeScreen()
    }
}

expect fun getPlatformName(): String