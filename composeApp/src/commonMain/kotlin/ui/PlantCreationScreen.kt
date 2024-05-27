package ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import dataClasses.Plant
import dataClasses.WateringInfo
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import plantyful.composeapp.generated.resources.Res
import plantyful.composeapp.generated.resources.description
import plantyful.composeapp.generated.resources.name
import plantyful.composeapp.generated.resources.species
import plantyful.composeapp.generated.resources.watering_cycle_in_days
import ui.detail.PlantDetailViewModel
import kotlin.time.Duration.Companion.days

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlantCreationScreen(
    paddingValues: PaddingValues,
    horizontalPadding: Dp,
    navController: NavController,
    savePlant: () -> Unit,
    viewModel: PlantDetailViewModel
) {
    viewModel.lastTimeWateredDatePickerState = rememberDatePickerState(
        initialDisplayMode = DisplayMode.Input,
        initialSelectedDateMillis = viewModel.lastTimeWatered
    )


    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(horizontal = horizontalPadding)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val galleryManager = rememberGalleryManager { imageBitmap ->
            imageBitmap?.let { viewModel.picture = it }
        }

        Button(galleryManager::launch) {
            Text("Choose picture")
        }

        viewModel.picture?.let { Image(it, null) }

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = viewModel.name,
            onValueChange = { value -> viewModel.name = value},
            label = { Text(stringResource(Res.string.name)) }
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = viewModel.species,
            onValueChange = { value -> viewModel.species = value},
            label = { Text(stringResource(Res.string.species)) }
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = viewModel.description,
            onValueChange = { value -> viewModel.description = value},
            label = { Text(stringResource(Res.string.description)) }
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = viewModel.cycle,
            onValueChange = { value -> viewModel.cycle = value},
            label = { Text(stringResource(Res.string.watering_cycle_in_days)) }
        )

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            viewModel.lastTimeWateredDatePickerState?.let {
                DatePicker(
                    state = it,
                    modifier = Modifier.padding(16.dp)
                )
            }


            Text(
                "Selected date timestamp: ${
                    viewModel.lastTimeWateredDatePickerState?.selectedDateMillis
                        ?: "no selection"
                }",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Text(
                "Selected date: ${
                    viewModel.lastTimeWateredDatePickerState?.selectedDateMillis?.let {
                        Instant.fromEpochMilliseconds(it)
                            .toLocalDateTime(TimeZone.currentSystemDefault())
                            .date
                    } ?: "no selection"}",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            Button(
                onClick = {
                    savePlant()
                    navController.popBackStack()
                },
                modifier = Modifier.padding(8.dp),
            ) {
                Text("Save")
            }
        }
    }

}