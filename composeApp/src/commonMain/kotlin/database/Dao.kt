package database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import dataClasses.Plant
import kotlinx.coroutines.flow.Flow

@Dao
interface PlantDao {
    @Insert
    suspend fun insert(vararg plants: Plant)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<Plant>)

    @Update
    suspend fun update(vararg plants: Plant)

    @Query("SELECT count(*) FROM Plant")
    suspend fun count(): Int

    @Query("SELECT * FROM Plant WHERE id=:id ")
    suspend fun getById(id: Long): Plant

    @Query("SELECT * FROM Plant")
    suspend fun getAll(): List<Plant>

    @Query("SELECT * FROM Plant")
    fun queryFlow(): Flow<List<Plant>>

    @Delete
    suspend fun delete(vararg plant: Plant)

}