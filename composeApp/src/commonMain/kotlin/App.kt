import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import cafe.adriel.voyager.navigator.Navigator
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.overview.PlantOverviewScreen

@Composable
@Preview
fun App() {
    MaterialTheme {
        Navigator(
            screen = PlantOverviewScreen(),
            onBackPressed = { currentScreen ->
                println("Navigator Pop screen #${currentScreen}")
                true
            }
        )
    }
}