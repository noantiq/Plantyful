package ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import dataClasses.Plant
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.format
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import plantyful.composeapp.generated.resources.Res
import plantyful.composeapp.generated.resources.watered_last_at
import ui.overview.getCurrentDate
import ui.overview.smallPadding
import kotlin.time.Duration.Companion.days

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PlantDetailScreen(
    paddingValues: PaddingValues,
    horizontalPadding: Dp,
    content: Plant,
    waterPlant: (Plant) -> Unit
) {
    var plant by remember { mutableStateOf(content) }
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(horizontal = horizontalPadding),
        verticalArrangement = Arrangement.spacedBy(smallPadding)
    ) {
        plant.species?.let { Text(it) }
        plant.description?.let { Text(it) }
        plant.picture?.let { Image(it, "alt") } //TODO
        plant.wateringInfo?.let { Text("alle ${it.cycle.inWholeDays} Tage") }
        plant.wateringInfo?.let { wateringInfo ->
            Text(
                stringResource(
                    Res.string.watered_last_at,
                    wateringInfo.lastTime.format(LocalDate.Formats.ISO)
                )
            )

            if (wateringInfo.lastTime.daysUntil(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date).days.inWholeDays > wateringInfo.cycle.inWholeDays) {
                Text("Achtung! Pflanze gießen")
            }
        }
        plant.ownedSince?.let { Text(it.format(LocalDate.Formats.ISO)) }
        Button(
            onClick = {
                plant = plant.copy(
                    wateringInfo = plant.wateringInfo?.copy(lastTime = getCurrentDate())
                )
                waterPlant(plant)
            }
        ) {
            Text("gießen")
        }
    }
}