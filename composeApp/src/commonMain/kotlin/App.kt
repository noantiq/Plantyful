import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.room.RoomDatabase
import dataClasses.Plant
import dataClasses.WateringInfo
import database.PlantDao
import database.PlantDatabase
import database.getRoomDatabase
import kotlinx.coroutines.launch
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.minus
import org.jetbrains.compose.resources.getString
import plantyful.composeapp.generated.resources.Res
import plantyful.composeapp.generated.resources.deleted_plant
import plantyful.composeapp.generated.resources.watered_plant
import ui.ScaffoldViewModel
import ui.detail.plantDetailScreen
import ui.overview.PlantOverviewViewModel
import ui.overview.getCurrentDate
import ui.overview.plantOverviewRoute
import ui.overview.plantOverviewScreen
import ui.plantCreationScreen
import ui.plantEditScreen
import ui.theme.PlantyTheme
import kotlin.time.Duration.Companion.days

var addDebugItems = false

@Composable
fun App(databaseBuilder: RoomDatabase.Builder<PlantDatabase>) {
    val database = remember { getRoomDatabase(databaseBuilder) }
    val plantDao = remember { database.getDao() }

    LaunchedEffect(Unit) { addDebugItems(plantDao) }

    PlantyTheme {
        val navController = rememberNavController()
        val scaffoldViewModel: ScaffoldViewModel = viewModel { ScaffoldViewModel() }
        val viewModel: PlantOverviewViewModel = viewModel(factory = PlantOverviewViewModel.factory(plantDao))
        val scope = rememberCoroutineScope()
        val waterPlant: (Plant) -> Unit = { plant: Plant ->
            viewModel.updateLastTimeWatered(
                plant.copy(
                    wateringInfo = plant.wateringInfo?.copy(
                        lastTime = getCurrentDate())
                )
            )
            scope.launch {
                scaffoldViewModel.snackBarMessage = getString(Res.string.watered_plant, plant.name)
            }
        }
        val deletePlant: (Plant) -> Unit = { plant: Plant ->
            viewModel.delete(plant)
            scope.launch {
                scaffoldViewModel.snackBarMessage = getString(Res.string.deleted_plant, plant.name)
            }
        }
        val addPlant = { plant: Plant -> viewModel.addPlant(plant) }
        val editPlant = { plant: Plant -> viewModel.editPlant(plant) }
        val getPlantById: (MutableState<Plant?>, Long) -> Unit = { plantState, id ->
            scope.launch {
                plantState.value = plantDao.getById(id)
            }
        }

        PlantyScaffold(
            viewModel = scaffoldViewModel,
            navController = navController
        ) { paddingValues, horizontalPadding ->
            NavHost(
                navController = navController,
                startDestination = plantOverviewRoute
            ) {
                plantOverviewScreen(
                    paddingValues = paddingValues,
                    horizontalPadding = horizontalPadding,
                    navController = navController,
                    scaffoldViewModel = scaffoldViewModel,
                    waterPlant = waterPlant,
                    viewModel = viewModel
                )
                plantDetailScreen(
                    paddingValues = paddingValues,
                    horizontalPadding = horizontalPadding,
                    navController = navController,
                    scaffoldViewModel = scaffoldViewModel,
                    waterPlant = waterPlant,
                    deletePlant = deletePlant,
                    getPlantById = getPlantById
                )
                plantCreationScreen(
                    paddingValues = paddingValues,
                    horizontalPadding = horizontalPadding,
                    navController = navController,
                    scaffoldViewModel = scaffoldViewModel,
                    addPlant = addPlant,
                )
                plantEditScreen(
                    paddingValues = paddingValues,
                    horizontalPadding = horizontalPadding,
                    navController = navController,
                    scaffoldViewModel = scaffoldViewModel,
                    editPlant = editPlant,
                    getPlantById = getPlantById
                )
            }
        }
    }
}

private suspend fun addDebugItems(plantDao: PlantDao) {
    if (addDebugItems) {
        plantDao.insertAll(
            (1 .. 10).map {
                Plant(
                    name = "Planty $it",
                    description = "Tolle Pflanze, braucht viel Sonne, viel Schatten und ganz viel Liebe.",
                    picture = null,
                    wateringInfo = WateringInfo(
                        cycle = 7.days,
                        lastTime = getCurrentDate().minus(DatePeriod(days = 14))
                    )
                )
            }
        )
        addDebugItems = false
    }

}
