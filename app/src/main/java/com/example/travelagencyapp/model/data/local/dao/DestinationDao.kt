package com.example.travelagencyapp.model.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.travelagencyapp.model.data.local.entity.DestinationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DestinationDao {
    @Query("SELECT * FROM destinations ORDER BY name")
    fun observeAll(): Flow<List<DestinationEntity>>

    @Query("SELECT * FROM destinations WHERE id = :id")
    suspend fun getById(id: Long): DestinationEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(destinations: List<DestinationEntity>)

    @Query("DELETE FROM destinations WHERE id = :id")
    suspend fun deleteById(id: Long)
}
