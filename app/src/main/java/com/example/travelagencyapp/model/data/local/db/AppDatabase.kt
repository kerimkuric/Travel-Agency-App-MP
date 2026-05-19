package com.example.travelagencyapp.model.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.travelagencyapp.model.data.local.dao.BookingDao
import com.example.travelagencyapp.model.data.local.dao.CategoryDao
import com.example.travelagencyapp.model.data.local.dao.CustomerDao
import com.example.travelagencyapp.model.data.local.dao.DestinationDao
import com.example.travelagencyapp.model.data.local.dao.TripDao
import com.example.travelagencyapp.model.data.local.entity.BookingEntity
import com.example.travelagencyapp.model.data.local.entity.CategoryEntity
import com.example.travelagencyapp.model.data.local.entity.CustomerEntity
import com.example.travelagencyapp.model.data.local.entity.DestinationEntity
import com.example.travelagencyapp.model.data.local.entity.TripCategoryCrossRef
import com.example.travelagencyapp.model.data.local.entity.TripEntity

@Database(
    entities = [
        DestinationEntity::class,
        CategoryEntity::class,
        TripEntity::class,
        TripCategoryCrossRef::class,
        CustomerEntity::class,
        BookingEntity::class,
    ],
    version = 1,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun destinationDao(): DestinationDao
    abstract fun categoryDao(): CategoryDao
    abstract fun tripDao(): TripDao
    abstract fun customerDao(): CustomerDao
    abstract fun bookingDao(): BookingDao
}
