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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
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
import kotlin.time.Duration.Companion.days

@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Composable
fun PlantCreationScreen(
    paddingValues: PaddingValues,
    horizontalPadding: Dp,
    navController: NavController,
    savePlant: (Plant) -> Unit,
    plant: Plant? = null
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val nameState = remember { mutableStateOf(plant?.name ?: "") }
    val speciesState = remember { mutableStateOf(plant?.species ?: "") }
    val pictureState = remember { mutableStateOf(plant?.picture) }
    val descriptionState = remember { mutableStateOf(plant?.description ?: "") }
    val cycleState = remember { mutableStateOf(plant?.wateringInfo?.cycle?.inWholeDays?.toInt()?.toString() ?: "") }
    val lastTimeWateredDatePickerState = rememberDatePickerState(
        initialSelectedDateMillis =
        plant?.wateringInfo?.lastTime?.toEpochDays()?.days?.inWholeMilliseconds
            ?: Clock.System.now().toEpochMilliseconds()
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
            imageBitmap?.let { pictureState.value = it }
        }

        Button(galleryManager::launch) {
            Text("Choose picture")
        }

        pictureState.value?.let { Image(it, null) }

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = nameState.value,
            onValueChange = { value -> nameState.value = value},
            label = { Text(stringResource(Res.string.name)) }
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = speciesState.value,
            onValueChange = { value -> speciesState.value = value},
            label = { Text(stringResource(Res.string.species)) }
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = descriptionState.value,
            onValueChange = { value -> descriptionState.value = value},
            label = { Text(stringResource(Res.string.description)) }
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = cycleState.value,
            onValueChange = { value -> cycleState.value = value},
            label = { Text(stringResource(Res.string.watering_cycle_in_days)) }
        )

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            DatePicker(state = lastTimeWateredDatePickerState, modifier = Modifier.padding(16.dp))

            Text(
                "Selected date timestamp: ${lastTimeWateredDatePickerState.selectedDateMillis ?: "no selection"}",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Text(
                "Selected date: ${
                    lastTimeWateredDatePickerState.selectedDateMillis?.let {
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
                    val dateState = lastTimeWateredDatePickerState.selectedDateMillis?.let {
                        Instant.fromEpochMilliseconds(it)
                            .toLocalDateTime(TimeZone.currentSystemDefault())
                            .date
                    }

                    savePlant(
                        (plant ?: Plant("")).copy(
                            name = nameState.value,
                            description = descriptionState.value.ifBlank { null },
                            species = speciesState.value.ifBlank { null },
                            picture = pictureState.value,
                            wateringInfo = WateringInfo(
                                cycle = cycleState.value.toInt().days,
                                lastTime = dateState!!
                            )
                        )
                    )

                    navController.popBackStack()
                },
                modifier = Modifier.padding(8.dp),
            ) {
                Text("Save")
            }
        }
    }

}