package com.example.travelagencyapp.model.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.travelagencyapp.model.data.local.entity.BookingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BookingDao {
    @Query("SELECT * FROM bookings ORDER BY bookedAtMillis DESC")
    fun observeAll(): Flow<List<BookingEntity>>

    @Query("SELECT COUNT(*) FROM bookings WHERE tripId = :tripId")
    fun observeBookingCountForTrip(tripId: Long): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(booking: BookingEntity): Long

    @Query("DELETE FROM bookings WHERE id = :bookingId")
    suspend fun deleteById(bookingId: Long)
}
