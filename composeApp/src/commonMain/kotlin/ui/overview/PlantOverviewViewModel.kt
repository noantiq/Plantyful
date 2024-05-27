package ui.overview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dataClasses.Plant
import database.PlantDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn


class PlantOverviewViewModel(private val dao: PlantDao) : ViewModel(CoroutineScope(Dispatchers.IO)) {

    companion object {
        fun factory(dao: PlantDao) = viewModelFactory { initializer { PlantOverviewViewModel(dao) } }
    }

    var plants: List<Plant> by mutableStateOf(emptyList())

    init {
        viewModelScope.launch {
            updatePlants(dao)
        }
    }

    fun updateLastTimeWatered(plant: Plant) {
        viewModelScope.launch {
            dao.update(plant)
            updatePlants(dao)
        }
    }

    fun addPlant(plant: Plant) {
        viewModelScope.launch {
            dao.insert(plant)
            updatePlants(dao)
        }
    }

    fun delete(plant: Plant) {
        viewModelScope.launch {
            dao.delete(plant)
            updatePlants(dao)
        }
    }

    fun editPlant(plant: Plant) {
        viewModelScope.launch {
            dao.update(plant)
            updatePlants(dao)
        }
    }

    private suspend fun updatePlants(plantDao: PlantDao){
        plants = plantDao.getAll()
            .sortedBy { it.name }
            .sortedBy { it.wateringInfo?.daysUntilDue }
    }
}

fun getCurrentDate(): LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault())

