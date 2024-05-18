import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import database.getPlantDatabaseBuilder

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Plantyful",
    ) {
        val database = remember { getPlantDatabaseBuilder() }
        App(database)
    }
}