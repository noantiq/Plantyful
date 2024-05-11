package database

import androidx.compose.ui.graphics.ImageBitmap
import androidx.room.TypeConverter
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlin.time.Duration

class Converters {
    @TypeConverter
    fun toLocalDate(value: String?): LocalDate? = value?.let { LocalDate.parse(it) }
    @TypeConverter
    fun fromLocalDate(date: LocalDate?): String? = date?.toString()

    @TypeConverter
    fun toDuration(value: String?): Duration? = value?.let { Duration.parse(it) }
    @TypeConverter
    fun fromDuration(duration: Duration?): String? = duration?.toString()

    @TypeConverter
    fun toImageBitmap(value: String?): ImageBitmap? = decodeBitmap(value)

    @TypeConverter
    fun fromImageBitmap(imageBitmap: ImageBitmap?): String? = encodeBitmap(imageBitmap)
}

expect fun encodeBitmap(bitmap: ImageBitmap?): String?
expect fun decodeBitmap(string: String?): ImageBitmap?
