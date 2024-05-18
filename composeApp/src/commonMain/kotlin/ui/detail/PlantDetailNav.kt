package ui.detail

import OnStartEffect
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import composeNavigation
import dataClasses.Plant
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import plantyful.composeapp.generated.resources.Res
import plantyful.composeapp.generated.resources.add_plant
import plantyful.composeapp.generated.resources.delete
import plantyful.composeapp.generated.resources.edit
import ui.ScaffoldViewModel
import ui.navigateToPlantEdit

const val plantDetailRoute = "detail"

fun NavController.navigateToPlantDetail(
    lifecycleOwner: LifecycleOwner,
    plantId: Long,
    navOptions: NavOptions? = null,
) {
    composeNavigation(lifecycleOwner) {
        this.navigate("$plantDetailRoute/$plantId", navOptions)
    }
}

@OptIn(ExperimentalResourceApi::class)
fun NavGraphBuilder.plantDetailScreen(
    paddingValues: PaddingValues,
    horizontalPadding: Dp,
    navController: NavController,
    scaffoldViewModel: ScaffoldViewModel,
    waterPlant: (Plant) -> Unit,
    deletePlant: (Plant) -> Unit,
    getPlantById: (MutableState<Plant?>, Long) -> Unit
) {
    val plantId = "plantId"
    composable(
        route = "$plantDetailRoute/{$plantId}"
    ) { backStackEntry ->
        backStackEntry.arguments?.getString(plantId)?.let { plantId ->
            val lifecycleOwner = LocalLifecycleOwner.current
            val plantState: MutableState<Plant?> = remember { mutableStateOf(null) }
            plantState.value?.let { plant ->
                OnStartEffect {
                    scaffoldViewModel.apply {
                        reset(plant.name)
                        floatingActionButton = {
                            FloatingActionButton(
                                onClick = { waterPlant(plant) },
                            ) {
                                Icon(
                                    imageVector = Icons.Default.WaterDrop,
                                    contentDescription = stringResource(Res.string.add_plant)
                                )
                            }
                        }
                        actions = {
                            IconButton(
                                onClick = {
                                    deletePlant(plant)
                                    navController.popBackStack()
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = stringResource(Res.string.delete)
                                )
                            }
                            IconButton(
                                onClick = {
                                    navController.navigateToPlantEdit(lifecycleOwner, plant.id)
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = stringResource(Res.string.edit)
                                )
                            }
                        }
                    }
                }
                PlantDetailScreen(
                    paddingValues = paddingValues,
                    horizontalPadding = horizontalPadding,
                    content = plant,
                    waterPlant = waterPlant
                )
            }

            LaunchedEffect(Unit) {
                if (plantState.value == null) {
                    getPlantById(plantState, plantId.toLong())
                }
            }
        }
    }
}
