package database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import dataClasses.Plant
import kotlinx.coroutines.Dispatchers

@Database(entities = [Plant::class], version = 1)
@TypeConverters(Converters::class)
abstract class PlantDatabase : RoomDatabase() {
    abstract fun getDao(): PlantDao
}

fun getRoomDatabase(
    builder: RoomDatabase.Builder<PlantDatabase>
): PlantDatabase {
    return builder
        //.addMigrations(MIGRATIONS)
        .fallbackToDestructiveMigrationOnDowngrade(true)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}
