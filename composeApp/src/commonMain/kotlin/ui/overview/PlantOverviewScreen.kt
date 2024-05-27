package ui.overview

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.Yard
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dataClasses.Plant
import org.jetbrains.compose.resources.stringResource
import plantyful.composeapp.generated.resources.Res
import plantyful.composeapp.generated.resources.biweekly
import plantyful.composeapp.generated.resources.daily
import plantyful.composeapp.generated.resources.every_x_days
import plantyful.composeapp.generated.resources.every_x_months
import plantyful.composeapp.generated.resources.every_x_weeks
import plantyful.composeapp.generated.resources.in_x_days
import plantyful.composeapp.generated.resources.monthly
import plantyful.composeapp.generated.resources.today
import plantyful.composeapp.generated.resources.tomorrow
import plantyful.composeapp.generated.resources.weekly
import plantyful.composeapp.generated.resources.x_days_ago
import plantyful.composeapp.generated.resources.yesterday
import ui.detail.navigateToPlantDetail

val smallPadding = 8.dp
val mediumPadding = 16.dp
val largePadding = 32.dp

val outerPadding = mediumPadding
val innerPadding = smallPadding

@Composable
fun PlantOverviewScreen(
    paddingValues: PaddingValues,
    horizontalPadding: Dp,
    navController: NavController,
    waterPlant: (Plant) -> Unit,
    viewModel: PlantOverviewViewModel
) {
    LazyColumn(
        contentPadding = paddingValues,
        modifier = Modifier.padding(horizontal = horizontalPadding),
        verticalArrangement = Arrangement.spacedBy(smallPadding)
    ) {
        items(viewModel.plants) { plant ->
            val navigateToDetail: () -> Unit = navController.navigateToPlantDetail(plant.id)
            PlantCard(
                plant = plant,
                navigateToDetail = navigateToDetail,
                waterPlant = { waterPlant(plant) }
            )
        }
    }
}

@Composable
private fun PlantCard(
    plant: Plant,
    navigateToDetail: () -> Unit,
    waterPlant: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = navigateToDetail),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceContainer)
    ) {
        Column(
            modifier = Modifier.padding(mediumPadding)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ){
                Box(Modifier.padding(end = mediumPadding)){
                    plant.croppedPicture?.let {
                        Card(modifier = Modifier.size(60.dp)) {
                            Image(
                                bitmap = it,
                                contentDescription = null,//TODO
                                contentScale = ContentScale.Crop
                            )
                        }

                    } ?: Icon(
                        imageVector = Icons.Default.Yard,
                        contentDescription = null
                    )
                }


                Column {
                    Text(
                        text = "${plant.name}${
                            plant.species?.let { 
                                " ($it)"
                            } ?: ""
                        }",
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    plant.description?.let {
                        Text(
                            text = it,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            plant.wateringInfo?.let { wateringInfo ->
                HorizontalDivider(modifier = Modifier.padding(vertical = smallPadding))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        InfoItem(
                            iconImageVector = Icons.Default.Repeat,
                            text = wateringInfo.cycle.inWholeDays.toInt().let { days ->
                                when {
                                    days == 1 -> stringResource(Res.string.daily)
                                    days == 7 -> stringResource(Res.string.weekly)
                                    days == 14 -> stringResource(Res.string.biweekly)
                                    days == 30 || days == 31 -> stringResource(Res.string.monthly)
                                    days % 30 <= 1 -> stringResource(Res.string.every_x_months, days.floorDiv(30))
                                    days % 7 == 0 -> stringResource(Res.string.every_x_weeks, days / 7)
                                    else -> stringResource(Res.string.every_x_days, days)
                                }
                            }
                        )

                        InfoItem(
                            iconImageVector = Icons.Default.Schedule,
                            text = wateringInfo.daysUntilDue.let { daysLeft ->
                                when {
                                    daysLeft == 1 -> stringResource(Res.string.tomorrow)
                                    daysLeft == -1 -> stringResource(Res.string.yesterday)
                                    daysLeft > 0 -> stringResource(Res.string.in_x_days, daysLeft)
                                    daysLeft < 0 -> stringResource(Res.string.x_days_ago, - daysLeft)
                                    else -> stringResource(Res.string.today)
                                }
                            },
                            isError = wateringInfo.isOverdue
                        )
                    }

                    Button(
                        modifier = Modifier.padding(start = mediumPadding),
                        onClick = waterPlant
                    ) {
                        Row {
                            Icon(
                                imageVector = Icons.Default.WaterDrop,
                                contentDescription = null
                            )
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun RowScope.InfoItem(
    iconImageVector: ImageVector,
    text: String,
    isError: Boolean = false
) {
    Row(
        modifier = Modifier.weight(1f),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.padding(end = smallPadding),
            imageVector = iconImageVector,
            contentDescription = null,
            tint = if (isError) {
                MaterialTheme.colorScheme.error
            } else {
                LocalContentColor.current
            }
        )
        Text(
            text = text,
            color = if (isError) {
                MaterialTheme.colorScheme.error
            } else {
                Color.Unspecified
            }
        )
    }
}
