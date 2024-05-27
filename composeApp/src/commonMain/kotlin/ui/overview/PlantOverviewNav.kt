package ui.overview

import OnStartEffect
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import dataClasses.Plant
import navigateOnClick
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import plantyful.composeapp.generated.resources.Res
import plantyful.composeapp.generated.resources.add_plant
import plantyful.composeapp.generated.resources.app_name
import ui.ScaffoldViewModel
import ui.navigateToPlantCreate

const val plantOverviewRoute = "overview"

@Composable
fun NavController.navigateToPlantOverview(navOptions: NavOptions? = null) =
    navigateOnClick(plantOverviewRoute, navOptions)

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
        val appName = stringResource(Res.string.app_name)
        OnStartEffect {
            scaffoldViewModel.apply {
                reset(appName)
                floatingActionButton = {
                    val navigateToCreate: () -> Unit = navController.navigateToPlantCreate()
                    FloatingActionButton(onClick = navigateToCreate) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(Res.string.add_plant)
                        )
                    }
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