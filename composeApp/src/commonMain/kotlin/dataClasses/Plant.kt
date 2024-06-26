package dataClasses

import androidx.compose.ui.graphics.ImageBitmap
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.daysUntil
import kotlinx.datetime.plus
import ui.crop
import ui.overview.getCurrentDate
import kotlin.time.Duration

@Entity
data class Plant(
    var name: String,
    var description: String? = null,
    var species: String? = null,
    var picture: ImageBitmap? = null,
    @Embedded var wateringInfo: WateringInfo? = null,
    var ownedSince: LocalDate? = null,
    @PrimaryKey(autoGenerate = true) val id: Long = 0
) {
    @Ignore
    val croppedPicture: ImageBitmap? = picture?.let {
        it.crop(
            it.width / 4,
            it.height / 4,
            it.width / 2,
            it.height / 2
        )
    }
}

data class WateringInfo(
    var cycle: Duration,
    val lastTime: LocalDate
) {
    @Ignore
    val dueDate: LocalDate = lastTime.plus(DatePeriod(days = cycle.inWholeDays.toInt()))
    @Ignore
    val daysUntilDue: Int = getCurrentDate().daysUntil(dueDate)
    @Ignore
    val isOverdue: Boolean = daysUntilDue < 0
}