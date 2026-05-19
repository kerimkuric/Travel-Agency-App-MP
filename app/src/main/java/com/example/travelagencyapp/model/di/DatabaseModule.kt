package com.example.travelagencyapp.model.di

import android.content.Context
import androidx.room.Room
import com.example.travelagencyapp.model.data.local.dao.BookingDao
import com.example.travelagencyapp.model.data.local.dao.CategoryDao
import com.example.travelagencyapp.model.data.local.dao.CustomerDao
import com.example.travelagencyapp.model.data.local.dao.DestinationDao
import com.example.travelagencyapp.model.data.local.dao.TripDao
import com.example.travelagencyapp.model.data.local.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "travel_agency.db",
        ).build()
    }

    @Provides
    fun provideDestinationDao(database: AppDatabase): DestinationDao = database.destinationDao()

    @Provides
    fun provideCategoryDao(database: AppDatabase): CategoryDao = database.categoryDao()

    @Provides
    fun provideTripDao(database: AppDatabase): TripDao = database.tripDao()

    @Provides
    fun provideCustomerDao(database: AppDatabase): CustomerDao = database.customerDao()

    @Provides
    fun provideBookingDao(database: AppDatabase): BookingDao = database.bookingDao()
}
