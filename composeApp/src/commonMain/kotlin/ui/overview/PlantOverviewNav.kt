package ui.overview

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import plantyful.composeapp.generated.resources.app_name
import ui.ScaffoldViewModel
import ui.navigateToPlantCreate

const val plantOverviewRoute = "overview"

fun NavController.navigateToPlantOverview(
    lifecycleOwner: LifecycleOwner,
    navOptions: NavOptions? = null,
) {
    composeNavigation(lifecycleOwner) {
        this.navigate(plantOverviewRoute, navOptions)
    }
}

@OptIn(ExperimentalResourceApi::class)
fun NavGraphBuilder.plantOverviewScreen(
    paddingValues: PaddingValues,
    horizontalPadding: Dp,
    navController: NavController,
    scaffoldViewModel: ScaffoldViewModel,
    waterPlant: (Plant) -> Unit,
    viewModel: PlantOverviewViewModel
) {
    composable(
        route = plantOverviewRoute
    ) {
        val lifecycleOwner = LocalLifecycleOwner.current
        scaffoldViewModel.apply {
            reset(stringResource(Res.string.app_name))
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        navController.navigateToPlantCreate(lifecycleOwner)
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(Res.string.add_plant)
                    )
                }
            }
        }
        PlantOverviewScreen(
            paddingValues = paddingValues,
            horizontalPadding = horizontalPadding,
            navController = navController,
            waterPlant = waterPlant,
            viewModel = viewModel
        )
    }
}
