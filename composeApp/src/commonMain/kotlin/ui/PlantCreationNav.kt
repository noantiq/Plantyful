package ui

import OnStartEffect
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import plantyful.composeapp.generated.resources.edit_plant
import plantyful.composeapp.generated.resources.save

const val plantCreationRoute = "create"

fun NavController.navigateToPlantCreate(
    lifecycleOwner: LifecycleOwner,
    navOptions: NavOptions? = null,
) {
    composeNavigation(lifecycleOwner) {
        this.navigate(plantCreationRoute, navOptions)
    }
}

fun NavController.navigateToPlantEdit(
    lifecycleOwner: LifecycleOwner,
    plantId: Long,
    navOptions: NavOptions? = null,
) {
    composeNavigation(lifecycleOwner) {
        this.navigate("$plantCreationRoute/$plantId", navOptions)
    }
}

@OptIn(ExperimentalResourceApi::class)
fun NavGraphBuilder.plantCreationScreen(
    paddingValues: PaddingValues,
    horizontalPadding: Dp,
    navController: NavController,
    scaffoldViewModel: ScaffoldViewModel,
    addPlant: (Plant) -> Unit,
) {
    composable(
        route = plantCreationRoute
    ) {
        scaffoldViewModel.apply {
            title = stringResource(Res.string.add_plant)
            floatingActionButton = {}
            actions = {
                IconButton(
                    onClick = {
                        //TODO
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Save,
                        contentDescription = stringResource(Res.string.save)
                    )
                }
            }
        }

        PlantCreationScreen(
            paddingValues = paddingValues,
            horizontalPadding = horizontalPadding,
            navController = navController,
            savePlant = addPlant
        )
    }
}

@OptIn(ExperimentalResourceApi::class)
fun NavGraphBuilder.plantEditScreen(
    paddingValues: PaddingValues,
    horizontalPadding: Dp,
    navController: NavController,
    scaffoldViewModel: ScaffoldViewModel,
    editPlant: (Plant) -> Unit,
    getPlantById: (MutableState<Plant?>, Long) -> Unit
) {
    val plantId = "plantId"
    composable(
        route = "$plantCreationRoute/{$plantId}"
    ) { backStackEntry ->
        backStackEntry.arguments?.getString(plantId)?.let { plantId ->
            val plantState: MutableState<Plant?> = remember { mutableStateOf(null) }

            plantState.value?.let { plant ->
                val title = stringResource(Res.string.edit_plant)
                OnStartEffect {
                    scaffoldViewModel.apply {
                        reset(title)
                        actions = {
                            IconButton(
                                onClick = {
                                    //TODO
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Save,
                                    contentDescription = stringResource(Res.string.save)
                                )
                            }
                        }
                    }
                }

                PlantCreationScreen(
                    paddingValues = paddingValues,
                    horizontalPadding = horizontalPadding,
                    navController = navController,
                    plant = plant,
                    savePlant = editPlant
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
