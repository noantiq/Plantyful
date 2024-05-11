package de.plantyful

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import de.plantyful.database.getPlantDatabaseBuilder

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App(
                getPlantDatabaseBuilder(applicationContext)
            )
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App(getPlantDatabaseBuilder(LocalContext.current))
}