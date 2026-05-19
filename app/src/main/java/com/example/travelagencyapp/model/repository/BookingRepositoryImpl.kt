package com.example.travelagencyapp.model.repository

import com.example.travelagencyapp.model.data.local.dao.BookingDao
import com.example.travelagencyapp.model.data.local.dao.CustomerDao
import com.example.travelagencyapp.model.data.local.entity.BookingEntity
import com.example.travelagencyapp.model.data.local.entity.CustomerEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookingRepositoryImpl @Inject constructor(
    private val bookingDao: BookingDao,
    private val customerDao: CustomerDao,
) : BookingRepository {

    override suspend fun createBooking(
        tripId: Long,
        customerName: String,
        customerEmail: String,
    ): Long {
        val existing = customerDao.findByEmail(customerEmail.trim())
        val customerId = existing?.id ?: customerDao.insert(
            CustomerEntity(
                fullName = customerName.trim(),
                email = customerEmail.trim(),
            )
        )

        return bookingDao.insert(
            BookingEntity(
                tripId = tripId,
                customerId = customerId,
                bookedAtMillis = System.currentTimeMillis(),
            )
        )
    }
}
