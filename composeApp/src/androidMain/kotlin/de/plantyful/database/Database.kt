package de.plantyful.database

import database.PlantDatabase
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

fun getPlantDatabaseBuilder(context: Context): RoomDatabase.Builder<PlantDatabase> {
    val dbFile = context.getDatabasePath("plant.db")
    return Room.databaseBuilder<PlantDatabase>(
        context = context.applicationContext,
        name = dbFile.absolutePath
    )
}