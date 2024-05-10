package ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import dataClasses.Plant
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.format
import kotlinx.datetime.format.*
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.overview.PlantOverviewViewModel
import kotlin.time.Duration.Companion.days

data class PlantDetailScreen(
    val viewModel: PlantOverviewViewModel,
    val index: Int,
    val onWatering: () -> Unit
) : Screen {
    @Composable
    override fun Content() {
        PlantDetail(viewModel.plants[index], onWatering)
    }
}

@Composable
fun PlantDetail(
    plant: Plant,
    onWatering: () -> Unit
) {
    Column {
        Text(plant.name)
        plant.description?.let { Text(it) }
        plant.picture?.let { Image(it, "alt") } //TODO
        plant.wateringCycle?.let { Text("alle ${it.days} Tage") }
        plant.lastTimeWatered?.let { wateringDate ->
            Text(wateringDate.format(LocalDate.Formats.ISO))
            plant.wateringCycle?.let { cycle ->
                if (wateringDate.daysUntil(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date).days.inWholeDays > cycle.days) {
                    Text("Achtung! Pflanze gießen")
                }
            }
        }
        plant.ownedSince?.let { Text(it.format(LocalDate.Formats.ISO)) }
        Button(
            onClick = {
                onWatering()
            }
        ) {
            Text("gießen")
        }
    }
}


/**
 * Previews
 */

@Composable
private fun PlantDetailScreenPreview(plant: Plant) {
    MaterialTheme {
        PlantDetail(plant) {}
    }
}

@Preview
@Composable
private fun PlantDetailScreenPreview1() = PlantDetailScreenPreview(
    Plant(
        name = "Planty"
    )
)

@Preview
@Composable
private fun PlantDetailScreenPreview2() = PlantDetailScreenPreview(
    Plant(
        name = "Planty",
        description = "This is a beautiful, yet small plant that needs a lot of sun and stuff!"
    )
)