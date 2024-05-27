import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import database.getPlantDatabaseBuilder

fun MainViewController() =
    ComposeUIViewController {
        val databaseBuilder = remember { getPlantDatabaseBuilder() }
        App(databaseBuilder)
    }