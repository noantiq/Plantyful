package database

import androidx.room.Room
import androidx.room.RoomDatabase
import platform.Foundation.NSHomeDirectory
import database.instantiateImpl

fun getPlantDatabaseBuilder(): RoomDatabase.Builder<PlantDatabase> =
    Room.databaseBuilder<PlantDatabase>(
        name = NSHomeDirectory() + "/plant.db",
        factory = { PlantDatabase::class.instantiateImpl() }
    )