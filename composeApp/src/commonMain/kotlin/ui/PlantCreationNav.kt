package ui

import OnStartEffect
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import dataClasses.Plant
import navigateOnClick
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import plantyful.composeapp.generated.resources.Res
import plantyful.composeapp.generated.resources.add_plant
import plantyful.composeapp.generated.resources.edit_plant
import plantyful.composeapp.generated.resources.save
import ui.detail.PlantDetailViewModel

const val plantCreationRoute = "create"

@Composable
fun NavController.navigateToPlantCreate(navOptions: NavOptions? = null) =
    navigateOnClick(plantCreationRoute, navOptions)

@Composable
fun NavController.navigateToPlantEdit(plantId: Long, navOptions: NavOptions? = null) =
    navigateOnClick("$plantCreationRoute/$plantId", navOptions)

fun NavGraphBuilder.plantCreationScreen(
    paddingValues: PaddingValues,
    horizontalPadding: Dp,
    navController: NavController,
    scaffoldViewModel: ScaffoldViewModel,
    addPlant: (Plant) -> Unit
) {
    composable(
        route = plantCreationRoute
    ) {
        CreateEditScreen(
            paddingValues = paddingValues,
            horizontalPadding = horizontalPadding,
            titleResource = Res.string.add_plant,
            navController = navController,
            scaffoldViewModel = scaffoldViewModel,
            savePlant = addPlant
        )
    }
}

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
                CreateEditScreen(
                    paddingValues = paddingValues,
                    horizontalPadding = horizontalPadding,
                    titleResource = Res.string.edit_plant,
                    navController = navController,
                    scaffoldViewModel = scaffoldViewModel,
                    savePlant = editPlant,
                    plant = plant
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

@Composable
private fun CreateEditScreen(
    paddingValues: PaddingValues,
    horizontalPadding: Dp,
    titleResource: StringResource,
    navController: NavController,
    scaffoldViewModel: ScaffoldViewModel,
    savePlant: (Plant) -> Unit,
    plant: Plant? = null
) {
    val viewModel: PlantDetailViewModel = viewModel(factory = PlantDetailViewModel.factory(plant))
    val onSave = { savePlant(viewModel.createModifiedPlant()) }

    val title = stringResource(titleResource)
    OnStartEffect {
        scaffoldViewModel.apply {
            reset(title)
            actions = {
                IconButton(onClick = onSave) {
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
        savePlant = onSave,
        viewModel = viewModel
    )
}
