package database

import androidx.room.Room
import androidx.room.RoomDatabase
import java.io.File

fun getPlantDatabaseBuilder(): RoomDatabase.Builder<PlantDatabase> {
    val dbFile = File(System.getProperty("java.io.tmpdir"), "user.db")
    return Room.databaseBuilder<PlantDatabase>(
        name = dbFile.absolutePath
    )
}