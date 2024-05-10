package ui.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dataClasses.Plant

class PlantDetailViewModel: ViewModel() {
    var plant: Plant? by mutableStateOf(null)
}