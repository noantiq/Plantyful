package database

import androidx.room.Room
import androidx.room.RoomDatabase
import java.io.File

fun getPlantDatabaseBuilder(): RoomDatabase.Builder<PlantDatabase> {
    //val dbFile = context.getDatabasePath("user.db")
    val dbFile = File(System.getProperty("java.io.tmpdir"), "plant.db")
    return Room.databaseBuilder<PlantDatabase>(
        name = dbFile.absolutePath
    )
}