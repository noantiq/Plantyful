package de.plantyful

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import database.getPlantDatabaseBuilder

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val plantDatabaseBuilder = remember { getPlantDatabaseBuilder(applicationContext) }
            App(plantDatabaseBuilder)
        }
    }
}