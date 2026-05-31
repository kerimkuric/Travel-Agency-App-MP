package com.example.travelagencyapp.model.di

import com.example.travelagencyapp.model.repository.BookingRepository
import com.example.travelagencyapp.model.repository.BookingRepositoryImpl
import com.example.travelagencyapp.model.repository.DestinationRepository
import com.example.travelagencyapp.model.repository.DestinationRepositoryImpl
import com.example.travelagencyapp.model.repository.TripNetworkRepository
import com.example.travelagencyapp.model.repository.TripNetworkRepositoryImpl
import com.example.travelagencyapp.model.repository.TripRepository
import com.example.travelagencyapp.model.repository.TripRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindTripRepository(impl: TripRepositoryImpl): TripRepository

    @Binds
    @Singleton
    abstract fun bindTripNetworkRepository(impl: TripNetworkRepositoryImpl): TripNetworkRepository

    @Binds
    @Singleton
    abstract fun bindDestinationRepository(impl: DestinationRepositoryImpl): DestinationRepository

    @Binds
    @Singleton
    abstract fun bindBookingRepository(impl: BookingRepositoryImpl): BookingRepository
}
