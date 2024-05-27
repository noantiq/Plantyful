package ui.detail

import androidx.compose.material3.DatePickerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dataClasses.Plant
import dataClasses.WateringInfo
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration.Companion.days

class PlantDetailViewModel(private val plant: Plant?) : ViewModel() {

    companion object {
        fun factory(plant: Plant?) = viewModelFactory { initializer { PlantDetailViewModel(plant) } }
    }

    var name by mutableStateOf(plant?.name ?: "")
    var species by mutableStateOf(plant?.species ?: "")
    var picture by mutableStateOf(plant?.picture)
    var description by mutableStateOf(plant?.description ?: "")
    var cycle by mutableStateOf(plant?.wateringInfo?.cycle?.inWholeDays?.toInt()?.toString() ?: "")
    val lastTimeWatered = plant?.wateringInfo?.lastTime?.toEpochDays()?.days?.inWholeMilliseconds
        ?: Clock.System.now().toEpochMilliseconds()
    var lastTimeWateredDatePickerState: DatePickerState? = null

    fun createModifiedPlant() = (plant ?: Plant("")).copy(
        name = name,
        description = description.ifBlank { null },
        species = species.ifBlank { null },
        picture = picture,
        wateringInfo = cycle.ifBlank { null }?.let { cycle ->
            lastTimeWateredDatePickerState?.selectedDateMillis?.let {
                Instant.fromEpochMilliseconds(it)
                    .toLocalDateTime(TimeZone.currentSystemDefault())
                    .date.let { date ->
                        try {
                            WateringInfo(
                                cycle = cycle.toInt().days,
                                lastTime = date
                            )
                        } catch (e: NumberFormatException) {
                            null
                        }
                    }
            }
        }
    )
}