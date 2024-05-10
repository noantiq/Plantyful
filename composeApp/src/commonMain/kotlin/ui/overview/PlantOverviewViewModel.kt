package ui.overview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dataClasses.Plant
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.DateTimePeriod
import kotlinx.datetime.LocalDate

class PlantOverviewViewModel: ViewModel() {

    var plants: List<Plant> by mutableStateOf(
        listOf(
            Plant(
                name = "Planty",
                description = "Tolle Pflanze! braucht viel Sonne und Wasser",
                wateringCycle = DatePeriod(days = 14),
                lastTimeWatered = LocalDate(2024, 4, 15),
                ownedSince = LocalDate(2024, 5, 1)
            ),
            Plant("Planty1"),
            Plant("Planty2"),
            Plant("Planty3")
        )
    )
}