package ui.overview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dataClasses.Plant
import database.PlantDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn


class PlantOverviewViewModel: ViewModel() {

    var dao: MutableStateFlow<PlantDao?> = MutableStateFlow(null)
    var plants: List<Plant> by mutableStateOf(emptyList())

    init {
        viewModelScope.launch {
            dao.collect { plantDao ->
                if (plantDao != null) {
                    updatePlants(plantDao)
                }
            }
        }
    }

    fun updateLastTimeWatered(plant: Plant) {
        viewModelScope.launch {
            dao.value?.let { plantDao ->
                plantDao.update(plant)
                updatePlants(plantDao)
            }
        }
    }

    fun addPlant(plant: Plant) {
        viewModelScope.launch {
            dao.value?.let { plantDao ->
                plantDao.insert(plant)
                updatePlants(plantDao)
            }
        }
    }

    fun delete(plant: Plant) {
        viewModelScope.launch {
            dao.value?.let { plantDao ->
                plantDao.delete(plant)
                updatePlants(plantDao)
            }
        }
    }

    fun editPlant(plant: Plant) {
        viewModelScope.launch {
            dao.value?.let { plantDao ->
                plantDao.update(plant)
                updatePlants(plantDao)
            }
        }
    }

    private suspend fun updatePlants(plantDao: PlantDao){
        plants = plantDao.getAll()
            .sortedBy { it.name }
            .sortedBy { it.wateringInfo?.daysUntilDue }
    }
}

fun getCurrentDate(): LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault())

