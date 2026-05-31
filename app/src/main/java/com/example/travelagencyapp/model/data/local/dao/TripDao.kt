package com.example.travelagencyapp.model.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.travelagencyapp.model.data.local.entity.TripCategoryCrossRef
import com.example.travelagencyapp.model.data.local.entity.TripEntity
import com.example.travelagencyapp.model.data.local.entity.TripWithDestinationAndCategories
import kotlinx.coroutines.flow.Flow

@Dao
interface TripDao {
    @Transaction
    @Query("SELECT * FROM trips ORDER BY title")
    fun observeTripsWithRelations(): Flow<List<TripWithDestinationAndCategories>>

    @Transaction
    @Query("SELECT * FROM trips WHERE id = :tripId")
    fun observeTripWithRelations(tripId: Long): Flow<TripWithDestinationAndCategories?>

    @Query("SELECT * FROM trips WHERE destinationId = :destinationId ORDER BY title")
    fun observeByDestination(destinationId: Long): Flow<List<TripEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(trips: List<TripEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategoryLinks(links: List<TripCategoryCrossRef>)

    @Query("DELETE FROM trips WHERE id = :tripId")
    suspend fun deleteById(tripId: Long)

    @Query("DELETE FROM trip_category_cross_ref")
    suspend fun deleteAllCategoryLinks()

    @Query("DELETE FROM trips")
    suspend fun deleteAllTrips()
}
