import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import database.getPlantDatabaseBuilder

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Plantyful",
    ) {
        App(getPlantDatabaseBuilder())
    }
}