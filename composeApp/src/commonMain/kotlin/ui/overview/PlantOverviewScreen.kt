package ui.overview

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dataClasses.Plant
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import ui.detail.PlantDetailScreen
import ui.detail.PlantDetailViewModel


class PlantOverviewScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: PlantOverviewViewModel = viewModel()
        PlantOverview(viewModel.plants, viewModel) {
            viewModel.plants = viewModel.plants.mapIndexed { index, plant ->
                if (index == it) {
                    plant.copy(lastTimeWatered = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date)
                } else plant
            }
        }
    }
}

@Composable
private fun PlantOverview(plants: List<Plant>, viewmodel: PlantOverviewViewModel, onWatering: (Int) -> Unit) {
    val navigator = LocalNavigator.currentOrThrow
    Column {
        plants.forEachIndexed { index, plant ->
            Row(
                modifier = Modifier.clickable {
                    navigator.push(PlantDetailScreen(viewmodel, index) {
                        onWatering(index)
                    })
                }
            ) {
                Text(plant.name)
            }
        }
    }
}
