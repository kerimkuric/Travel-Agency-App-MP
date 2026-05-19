package com.example.travelagencyapp.model.repository

interface BookingRepository {
    suspend fun createBooking(
        tripId: Long,
        customerName: String,
        customerEmail: String,
    ): Long
}
