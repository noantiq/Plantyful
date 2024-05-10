package dataClasses

import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.DateTimePeriod
import kotlinx.datetime.DateTimeUnit
import kotlin.time.Duration
import kotlinx.datetime.LocalDate

data class Plant (
    var name: String,
    var description: String? = null,
    var picture: ImageBitmap? = null,
    var wateringCycle: DatePeriod? = null,
    val lastTimeWatered: LocalDate? = null,
    var ownedSince: LocalDate? = null
)